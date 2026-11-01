package com.example.martinichef_grupo14.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val cargando: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(nuevoEmail: String) {
        _uiState.update {
            it.copy(
                email = nuevoEmail,
                error = null
            )
        }
    }

    fun onPasswordChanged(nuevaPassword: String) {
        _uiState.update {
            it.copy(
                password = nuevaPassword,
                error = null
            )
        }
    }

    fun ingresar(onIngresoCorrecto: () -> Unit) {
        if (!validarCampos()) return

        val state = _uiState.value

        val correoCorrecto = state.email.trim().equals(
            "demo@martinichef.cl",
            ignoreCase = true
        )

        val passwordCorrecta = state.password == "Martini123"

        if (correoCorrecto && passwordCorrecta) {
            _uiState.update {
                it.copy(
                    password = "",
                    error = null
                )
            }

            onIngresoCorrecto()

        } else {
            _uiState.update {
                it.copy(
                    error = "Correo o contraseña de demostración incorrectos"
                )
            }
        }
    }

    fun validarCampos(): Boolean {
        val state = _uiState.value
        val correo = state.email.trim()

        val mensajeError = when {
            correo.isBlank() ->
                "Ingresa tu correo electrónico"

            !Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                "Ingresa un correo electrónico válido"

            state.password.isBlank() ->
                "Ingresa tu contraseña"

            else -> null
        }

        _uiState.update {
            it.copy(error = mensajeError)
        }

        return mensajeError == null
    }
}