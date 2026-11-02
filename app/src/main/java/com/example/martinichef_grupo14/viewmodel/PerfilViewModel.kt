package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.PerfilDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.UUID

class PerfilViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val contexto = application.applicationContext
    private val perfilDataStore = PerfilDataStore(contexto)

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri = _imagenUri.asStateFlow()

    private val _cargando = MutableStateFlow(true)
    val cargando = _cargando.asStateFlow()

    private val _guardando = MutableStateFlow(false)
    val guardando = _guardando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val fotoGuardada = withContext(Dispatchers.IO) {
                    val nombre =
                        perfilDataStore.archivoFotoFlow.first()

                    if (nombre != null) {
                        val carpeta = File(
                            contexto.filesDir,
                            "fotos_perfil"
                        )

                        val archivo = File(carpeta, nombre)

                        if (archivo.exists()) {
                            Uri.fromFile(archivo)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                }

                _imagenUri.value = fotoGuardada

            } catch (e: IOException) {
                _error.value =
                    "No se pudo recuperar la foto de perfil."

            } finally {
                _cargando.value = false
            }
        }
    }

    fun setImagenUri(uri: Uri?) {
        if (uri == null) return
        if (_cargando.value || _guardando.value) return

        _guardando.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val nuevaImagen = withContext(Dispatchers.IO) {
                    guardarCopia(uri)
                }

                _imagenUri.value = nuevaImagen

            } catch (e: IOException) {
                _error.value =
                    "No se pudo guardar la foto. Intenta nuevamente."

            } catch (e: SecurityException) {
                _error.value =
                    "No se pudo acceder a la imagen seleccionada."

            } finally {
                _guardando.value = false
            }
        }
    }

    private suspend fun guardarCopia(uri: Uri): Uri {
        val carpeta = File(
            contexto.filesDir,
            "fotos_perfil"
        )

        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw IOException("No se pudo crear la carpeta de fotos.")
        }

        val tipo = contexto.contentResolver.getType(uri)

        val extension = tipo?.let {
            MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(it)
        } ?: "jpg"

        val nombreNuevo = "foto_${UUID.randomUUID()}.$extension"
        val archivoNuevo = File(carpeta, nombreNuevo)

        val nombreAnterior =
            perfilDataStore.archivoFotoFlow.first()

        var guardado = false

        try {
            val entrada = contexto.contentResolver
                .openInputStream(uri)
                ?: throw IOException("No se pudo abrir la imagen.")

            entrada.use { origen ->
                archivoNuevo.outputStream().use { destino ->
                    origen.copyTo(destino)
                }
            }

            if (archivoNuevo.length() == 0L) {
                throw IOException("La imagen está vacía.")
            }

            perfilDataStore.guardarNombreArchivo(nombreNuevo)
            guardado = true

            if (nombreAnterior != null && nombreAnterior != nombreNuevo) {
                runCatching {
                    File(carpeta, nombreAnterior).delete()
                }
            }

            return Uri.fromFile(archivoNuevo)

        } finally {
            if (!guardado) {
                runCatching {
                    archivoNuevo.delete()
                }
            }
        }
    }
}