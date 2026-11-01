package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.EstadoDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.CancellationException
import java.io.IOException

class EstadoViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = EstadoDataStore(application)

    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo.asStateFlow()

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje.asStateFlow()
    private val _guardando = MutableStateFlow(false)
    val guardando: StateFlow<Boolean> = _guardando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.estadoFlow.collect { valorGuardado ->
                _activo.value = valorGuardado
            }
        }
    }

    fun alternarEstado() {
        val estadoActual = _activo.value ?: return

        if (_guardando.value) return

        val nuevoEstado = !estadoActual

        _guardando.value = true
        _mostrarMensaje.value = false
        _error.value = null

        viewModelScope.launch {
            try {
                dataStore.guardarEstado(nuevoEstado)

                _mostrarMensaje.value = true
                delay(1500)
                _mostrarMensaje.value = false

            } catch (e: CancellationException) {
                throw e

            } catch (e: IOException) {
                _error.value = "No se pudo guardar el estado"

            } finally {
                _guardando.value = false
            }
        }
    }
}