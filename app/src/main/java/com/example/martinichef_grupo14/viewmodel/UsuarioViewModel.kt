package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.UsuarioDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.GeneralSecurityException

data class RegistroUiState(
    val nombre: String = "",
    val nombreError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val direccion: String = "",
    val direccionError: String? = null,
    val aceptaTerminos: Boolean = false,
    val terminosError: String? = null,
    val esValido: Boolean = false,
    val guardando: Boolean = false,
    val errorGuardado: String? = null,
    val cargandoPerfil: Boolean = true
)

class UsuarioViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val usuarioDataStore = UsuarioDataStore(application)

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val perfil = usuarioDataStore.perfilFlow.first()

                if (perfil != null) {
                    _uiState.update {
                        it.copy(
                            nombre = perfil.nombre,
                            email = perfil.email,
                            direccion = perfil.direccion
                        )
                    }
                }
            } catch (_: IOException) {
                _uiState.update {
                    it.copy(
                        errorGuardado = "No se pudo recuperar el perfil."
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(cargandoPerfil = false)
                }
            }
        }
    }

    fun onNombreChanged(nuevoNombre: String) {
        _uiState.update {
            it.copy(
                nombre = nuevoNombre,
                nombreError = if (nuevoNombre.isBlank()) {
                    "El nombre es obligatorio"
                } else null,
                esValido = false,
                errorGuardado = null
            )
        }
    }

    fun onEmailChanged(nuevoEmail: String) {
        val correo = nuevoEmail.trim()

        _uiState.update {
            it.copy(
                email = nuevoEmail,
                emailError = when {
                    correo.isBlank() -> "El correo es obligatorio"

                    !Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                        "Ingresa un correo electrónico válido"

                    else -> null
                },
                esValido = false,
                errorGuardado = null
            )
        }
    }

    fun onPasswordChanged(nuevaPassword: String) {
        _uiState.update {
            it.copy(
                password = nuevaPassword,
                passwordError = if (
                    nuevaPassword.isBlank() || nuevaPassword.length < 6
                ) {
                    "La contraseña debe tener al menos 6 caracteres"
                } else null,
                esValido = false,
                errorGuardado = null
            )
        }
    }

    fun onDireccionChanged(nuevaDireccion: String) {
        _uiState.update {
            it.copy(
                direccion = nuevaDireccion,
                direccionError = if (nuevaDireccion.isBlank()) {
                    "La dirección es obligatoria"
                } else null,
                esValido = false,
                errorGuardado = null
            )
        }
    }

    fun onTerminosChanged(acepta: Boolean) {
        _uiState.update {
            it.copy(
                aceptaTerminos = acepta,
                terminosError = if (!acepta) {
                    "Debes aceptar los términos"
                } else null,
                esValido = false,
                errorGuardado = null
            )
        }
    }

    fun guardarRegistro(onGuardado: () -> Unit) {
        if (_uiState.value.guardando || _uiState.value.cargandoPerfil) {
            return
        }

        if (!validarFormulario()) return

        val datos = _uiState.value

        _uiState.update {
            it.copy(
                guardando = true,
                errorGuardado = null
            )
        }

        viewModelScope.launch {
            try {
                usuarioDataStore.guardarPerfil(
                    nombre = datos.nombre,
                    email = datos.email,
                    direccion = datos.direccion,
                    password = datos.password
                )

                _uiState.update {
                    it.copy(
                        nombre = datos.nombre.trim(),
                        email = datos.email.trim(),
                        direccion = datos.direccion.trim(),
                        password = "",
                        esValido = true
                    )
                }

                onGuardado()
            } catch (_: IOException) {
                mostrarErrorGuardado()
            } catch (_: GeneralSecurityException) {
                mostrarErrorGuardado()
            } finally {
                _uiState.update {
                    it.copy(guardando = false)
                }
            }
        }
    }

    private fun mostrarErrorGuardado() {
        _uiState.update {
            it.copy(
                esValido = false,
                errorGuardado = "No se pudo guardar la cuenta. Intenta nuevamente."
            )
        }
    }

    fun validarFormulario(): Boolean {
        val datos = _uiState.value

        val nombreError = if (datos.nombre.isBlank()) {
            "El nombre es obligatorio"
        } else null

        val emailError = if (
            !Patterns.EMAIL_ADDRESS.matcher(datos.email.trim()).matches()
        ) {
            "Correo inválido"
        } else null

        val passwordError = if (
            datos.password.isBlank() || datos.password.length < 6
        ) {
            "Mínimo 6 caracteres"
        } else null

        val direccionError = if (datos.direccion.isBlank()) {
            "La dirección es obligatoria"
        } else null

        val terminosError = if (!datos.aceptaTerminos) {
            "Debes aceptar los términos"
        } else null

        val tieneErrores =
            nombreError != null ||
                    emailError != null ||
                    passwordError != null ||
                    direccionError != null ||
                    terminosError != null

        _uiState.update {
            it.copy(
                nombreError = nombreError,
                emailError = emailError,
                passwordError = passwordError,
                direccionError = direccionError,
                terminosError = terminosError,
                esValido = !tieneErrores
            )
        }

        return !tieneErrores
    }
}