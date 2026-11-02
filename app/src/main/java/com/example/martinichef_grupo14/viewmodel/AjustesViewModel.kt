package com.example.martinichef_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinichef_grupo14.data.AjustesDataStore
import com.example.martinichef_grupo14.model.PreferenciasApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class AjustesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val ajustesDataStore = AjustesDataStore(application)

    private val _preferencias =
        MutableStateFlow<PreferenciasApp?>(null)

    val preferencias = _preferencias.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                ajustesDataStore.preferenciasFlow.collect { guardadas ->
                    _preferencias.value = guardadas
                }

            } catch (e: IOException) {
                _error.value = "No se pudieron recuperar los ajustes."
                _preferencias.value = PreferenciasApp()
            }
        }
    }

    fun cambiarModoOscuro(activado: Boolean) {
        viewModelScope.launch {
            _error.value = null

            try {
                ajustesDataStore.guardarModoOscuro(activado)

            } catch (e: IOException) {
                _error.value = "No se pudo guardar el modo de apariencia."
            }
        }
    }

    fun cambiarEscalaTexto(escala: Float) {
        viewModelScope.launch {
            _error.value = null

            try {
                ajustesDataStore.guardarEscalaTexto(escala)

            } catch (e: IOException) {
                _error.value = "No se pudo guardar el tamaño del texto."
            }
        }
    }
}