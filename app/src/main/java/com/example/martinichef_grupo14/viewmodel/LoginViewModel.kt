package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.UsuarioDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.GeneralSecurityException

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val cargando: Boolean = false
)

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val usuarioDataStore = UsuarioDataStore(application)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(nuevoEmail: String) {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(email = nuevoEmail, error = null)
        }
    }

    fun onPasswordChanged(nuevaPassword: String) {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(password = nuevaPassword, error = null)
        }
    }

    fun ingresar(onIngresoCorrecto: () -> Unit) {
        if (_uiState.value.cargando) return
        if (!validarCampos()) return

        val datos = _uiState.value

        _uiState.update {
            it.copy(cargando = true, error = null)
        }

        viewModelScope.launch {
            try {
                if (!usuarioDataStore.tieneCuentaRegistrada()) {
                    _uiState.update {
                        it.copy(
                            error = "Primero registra tu cuenta en este dispositivo."
                        )
                    }
                    return@launch
                }

                val ingresoCorrecto = usuarioDataStore.verificarCredenciales(
                    email = datos.email,
                    password = datos.password
                )

                if (ingresoCorrecto) {
                    _uiState.update {
                        it.copy(
                            email = datos.email.trim(),
                            password = "",
                            error = null
                        )
                    }

                    onIngresoCorrecto()
                } else {
                    _uiState.update {
                        it.copy(
                            error = "El correo o la contraseña son incorrectos."
                        )
                    }
                }
            } catch (_: IOException) {
                mostrarErrorLectura()
            } catch (_: GeneralSecurityException) {
                mostrarErrorLectura()
            } catch (_: IllegalArgumentException) {
                mostrarErrorLectura()
            } finally {
                _uiState.update {
                    it.copy(cargando = false)
                }
            }
        }
    }

    private fun mostrarErrorLectura() {
        _uiState.update {
            it.copy(
                error = "No se pudo comprobar la cuenta guardada. Intenta nuevamente."
            )
        }
    }

    fun validarCampos(): Boolean {
        val datos = _uiState.value
        val correo = datos.email.trim()

        val mensaje = when {
            correo.isBlank() ->
                "Ingresa tu correo electrónico"

            !Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                "Ingresa un correo electrónico válido"

            datos.password.isBlank() ->
                "Ingresa tu contraseña"

            else -> null
        }

        _uiState.update {
            it.copy(error = mensaje)
        }

        return mensaje == null
    }
}