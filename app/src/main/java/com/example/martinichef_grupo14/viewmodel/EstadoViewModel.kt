package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.EstadoDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EstadoViewModel(application: Application) : AndroidViewModel(application) {

    private val estadoDataStore = EstadoDataStore(application)

    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    init {
        cargarEstado()
    }

    fun cargarEstado() {
        viewModelScope.launch {
            delay(1500) // Simula la carga de datos para mostrar el loader
            _activo.value = estadoDataStore.obtenerEstado().first() ?: false
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {
            val nuevoValor = !(_activo.value ?: false)
            estadoDataStore.guardarEstado(nuevoValor)
            _activo.value = nuevoValor

            _mostrarMensaje.value = true
            delay(2000) // Mantiene el mensaje visible durante 2 segundos
            _mostrarMensaje.value = false
        }
    }
}