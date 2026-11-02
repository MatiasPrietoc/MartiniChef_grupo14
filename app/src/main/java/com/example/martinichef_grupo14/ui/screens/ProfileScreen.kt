package com.example.martinichef_grupo14.ui.screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.martinichef_grupo14.ui.components.ImagenInteligente
import com.example.martinichef_grupo14.viewmodel.PerfilViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val imagenUri by viewModel.imagenUri.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val guardando by viewModel.guardando.collectAsState()
    val error by viewModel.error.collectAsState()

    var tempCamaraUriTexto by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    // Espera a terminar la recuperación antes de guardar otra imagen.
    fun guardarImagen(uri: Uri) {
        scope.launch {
            viewModel.cargando.first { !it }
            viewModel.setImagenUri(uri)
        }
    }

    fun crearTempUri(): Uri {
        val archivo = File.createTempFile(
            "foto_temp_",
            ".jpg",
            context.cacheDir
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            archivo
        )
    }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { guardarImagen(it) }
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito: Boolean ->
        val uri = tempCamaraUriTexto?.let { Uri.parse(it) }

        if (exito && uri != null) {
            guardarImagen(uri)
        }

        tempCamaraUriTexto = null
    }

    fun abrirCamara() {
        try {
            val uri = crearTempUri()
            tempCamaraUriTexto = uri.toString()
            camaraLauncher.launch(uri)

        } catch (e: ActivityNotFoundException) {
            tempCamaraUriTexto = null

            Toast.makeText(
                context,
                "No hay una aplicación de cámara disponible.",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: IOException) {
            tempCamaraUriTexto = null

            Toast.makeText(
                context,
                "No se pudo preparar el archivo para la foto.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido: Boolean ->
        if (concedido) {
            abrirCamara()
        } else {
            Toast.makeText(
                context,
                "Se requiere permiso de cámara.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (cargando) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenInteligente(imagenUri = imagenUri)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Foto de perfil",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Puedes cambiar tu foto cuando quieras",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "La imagen se guarda en este dispositivo.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        if (guardando) {
            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Guardando foto...")
        }

        error?.let { mensaje ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                galeriaLauncher.launch("image/*")
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Abrir Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val permiso = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                )

                if (permiso == PackageManager.PERMISSION_GRANTED) {
                    abrirCamara()
                } else {
                    permisoCamaraLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar Foto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                navController.popBackStack(
                    route = "pantalla_principal",
                    inclusive = false
                )
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio")
        }
    }
}