package com.example.martinichef_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegistroUiState(
    val nombre: String = "",
    val nombreError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val esValido: Boolean = false
)

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNombreChanged(nuevoNombre: String) {
        _uiState.update { current ->
            current.copy(
                nombre = nuevoNombre,
                nombreError = if (nuevoNombre.isBlank()) "El nombre no puede estar vacío" else null
            )
        }
    }

    fun onEmailChanged(nuevoEmail: String) {
        val emailValido = android.util.Patterns.EMAIL_ADDRESS.matcher(nuevoEmail).matches()
        _uiState.update { current ->
            current.copy(
                email = nuevoEmail,
                emailError = when {
                    nuevoEmail.isBlank() -> "El correo es obligatorio"
                    !emailValido -> "Ingresa un correo electrónico válido"
                    else -> null
                }
            )
        }
    }

    fun onPasswordChanged(nuevaPassword: String) {
        _uiState.update { current ->
            current.copy(
                password = nuevaPassword,
                passwordError = when {
                    nuevaPassword.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
                    else -> null
                }
            )
        }
    }

    fun validarFormulario(): Boolean {
        val state = _uiState.value
        val nombreErr = if (state.nombre.isBlank()) "El nombre es obligatorio" else null
        val emailErr = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) "Correo inválido" else null
        val passErr = if (state.password.length < 6) "Mínimo 6 caracteres" else null

        val tieneErrores = nombreErr != null || emailErr != null || passErr != null

        _uiState.update {
            it.copy(
                nombreError = nombreErr,
                emailError = emailErr,
                passwordError = passErr,
                esValido = !tieneErrores
            )
        }

        return !tieneErrores
    }
}