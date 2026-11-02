package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.UsuarioDataStore
import com.example.martinichef_grupo14.model.PerfilGuardado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class MisDatosUiState(
    val perfilGuardado: PerfilGuardado? = null,
    val nombre: String = "",
    val email: String = "",
    val direccion: String = "",
    val cargando: Boolean = true,
    val editando: Boolean = false,
    val guardando: Boolean = false,
    val error: String? = null,
    val mensaje: String? = null
)

class MisDatosViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val usuarioDataStore = UsuarioDataStore(application)

    private val _uiState = MutableStateFlow(MisDatosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                usuarioDataStore.perfilFlow.collect { perfil ->
                    _uiState.update { actual ->
                        if (actual.editando) {
                            actual.copy(
                                perfilGuardado = perfil,
                                cargando = false
                            )
                        } else {
                            actual.copy(
                                perfilGuardado = perfil,
                                nombre = perfil?.nombre.orEmpty(),
                                email = perfil?.email.orEmpty(),
                                direccion = perfil?.direccion.orEmpty(),
                                cargando = false
                            )
                        }
                    }
                }
            } catch (_: IOException) {
                _uiState.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudieron cargar tus datos."
                    )
                }
            }
        }
    }

    fun comenzarEdicion() {
        val perfil = _uiState.value.perfilGuardado ?: return

        if (_uiState.value.guardando) return

        _uiState.update {
            it.copy(
                nombre = perfil.nombre,
                email = perfil.email,
                direccion = perfil.direccion,
                editando = true,
                error = null,
                mensaje = null
            )
        }
    }

    fun onNombreChanged(nombre: String) {
        if (!_uiState.value.editando || _uiState.value.guardando) return

        _uiState.update {
            it.copy(nombre = nombre, error = null)
        }
    }

    fun onEmailChanged(email: String) {
        if (!_uiState.value.editando || _uiState.value.guardando) return

        _uiState.update {
            it.copy(email = email, error = null)
        }
    }

    fun onDireccionChanged(direccion: String) {
        if (!_uiState.value.editando || _uiState.value.guardando) return

        _uiState.update {
            it.copy(direccion = direccion, error = null)
        }
    }

    fun cancelarEdicion() {
        if (_uiState.value.guardando) return

        val perfil = _uiState.value.perfilGuardado ?: return

        _uiState.update {
            it.copy(
                nombre = perfil.nombre,
                email = perfil.email,
                direccion = perfil.direccion,
                editando = false,
                error = null,
                mensaje = null
            )
        }
    }

    fun guardarCambios() {
        val datos = _uiState.value

        if (!datos.editando || datos.guardando) return

        val nombre = datos.nombre.trim()
        val email = datos.email.trim()
        val direccion = datos.direccion.trim()

        val errorValidacion = when {
            nombre.isBlank() ->
                "El nombre no puede estar vacío."

            email.isBlank() ->
                "El correo es obligatorio."

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Ingresa un correo electrónico válido."

            direccion.isBlank() ->
                "La dirección no puede estar vacía."

            else -> null
        }

        if (errorValidacion != null) {
            _uiState.update {
                it.copy(error = errorValidacion)
            }
            return
        }

        _uiState.update {
            it.copy(
                guardando = true,
                error = null,
                mensaje = null
            )
        }

        viewModelScope.launch {
            try {
                // Al omitir password, se conserva la contraseña actual.
                usuarioDataStore.guardarPerfil(
                    nombre = nombre,
                    email = email,
                    direccion = direccion
                )

                val perfilActualizado = PerfilGuardado(
                    nombre = nombre,
                    email = email,
                    direccion = direccion
                )

                _uiState.update {
                    it.copy(
                        perfilGuardado = perfilActualizado,
                        nombre = nombre,
                        email = email,
                        direccion = direccion,
                        editando = false,
                        mensaje = "Tus datos se actualizaron correctamente."
                    )
                }
            } catch (_: IOException) {
                _uiState.update {
                    it.copy(
                        error = "No se pudieron guardar los cambios. Intenta nuevamente."
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(guardando = false)
                }
            }
        }
    }
}