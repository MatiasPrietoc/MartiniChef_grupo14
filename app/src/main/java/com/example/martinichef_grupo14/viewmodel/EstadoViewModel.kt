package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.EstadoDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EstadoViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = EstadoDataStore(application)

    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo.asStateFlow()

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.estadoFlow.collect { valorGuardado ->
                _activo.value = valorGuardado
            }
        }
    }

    fun alternarEstado() {
        val nuevoEstado = !(_activo.value ?: false)
        _activo.value = nuevoEstado
        _mostrarMensaje.value = true

        viewModelScope.launch {
            dataStore.guardarEstado(nuevoEstado)
        }
    }
}