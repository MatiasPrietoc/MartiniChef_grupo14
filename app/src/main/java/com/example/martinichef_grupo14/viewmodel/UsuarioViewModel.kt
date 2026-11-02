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

    // Recupera el perfil guardado una sola vez al crear el ViewModel.
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

            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        errorGuardado =
                            "No se pudo recuperar el perfil guardado."
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
                    "El nombre no puede estar vacío"
                } else {
                    null
                },
                esValido = false
            )
        }
    }

    fun onEmailChanged(nuevoEmail: String) {
        val emailValido = Patterns.EMAIL_ADDRESS
            .matcher(nuevoEmail)
            .matches()

        _uiState.update {
            it.copy(
                email = nuevoEmail,
                emailError = when {
                    nuevoEmail.isBlank() ->
                        "El correo es obligatorio"

                    !emailValido ->
                        "Ingresa un correo electrónico válido"

                    else -> null
                },
                esValido = false
            )
        }
    }

    fun onPasswordChanged(nuevaPassword: String) {
        _uiState.update {
            it.copy(
                password = nuevaPassword,
                passwordError = if (nuevaPassword.length < 6) {
                    "La contraseña debe tener al menos 6 caracteres"
                } else {
                    null
                },
                esValido = false
            )
        }
    }

    fun onDireccionChanged(nuevaDireccion: String) {
        _uiState.update {
            it.copy(
                direccion = nuevaDireccion,
                direccionError = if (nuevaDireccion.isBlank()) {
                    "La dirección es obligatoria"
                } else {
                    null
                },
                esValido = false
            )
        }
    }

    fun onTerminosChanged(acepta: Boolean) {
        _uiState.update {
            it.copy(
                aceptaTerminos = acepta,
                terminosError = if (!acepta) {
                    "Debes aceptar los términos"
                } else {
                    null
                },
                esValido = false
            )
        }
    }

    fun guardarRegistro(onGuardado: () -> Unit) {
        if (_uiState.value.guardando) return

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
                    direccion = datos.direccion
                )

                onGuardado()

            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        errorGuardado =
                            "No se pudo guardar el perfil. Intenta nuevamente."
                    )
                }

            } finally {
                _uiState.update {
                    it.copy(guardando = false)
                }
            }
        }
    }

    fun validarFormulario(): Boolean {
        val state = _uiState.value

        val nombreErr = if (state.nombre.isBlank()) {
            "El nombre es obligatorio"
        } else {
            null
        }

        val emailErr = if (
            !Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        ) {
            "Correo inválido"
        } else {
            null
        }

        val passErr = if (state.password.length < 6) {
            "Mínimo 6 caracteres"
        } else {
            null
        }

        val direccionErr = if (state.direccion.isBlank()) {
            "La dirección es obligatoria"
        } else {
            null
        }

        val terminosErr = if (!state.aceptaTerminos) {
            "Debes aceptar los términos"
        } else {
            null
        }

        val tieneErrores =
            nombreErr != null ||
                    emailErr != null ||
                    passErr != null ||
                    direccionErr != null ||
                    terminosErr != null

        _uiState.update {
            it.copy(
                nombreError = nombreErr,
                emailError = emailErr,
                passwordError = passErr,
                direccionError = direccionErr,
                terminosError = terminosErr,
                esValido = !tieneErrores
            )
        }

        return !tieneErrores
    }
}