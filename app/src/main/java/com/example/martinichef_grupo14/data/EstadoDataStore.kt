package com.example.martinichef_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Instancia de DataStore asociada al contexto de la aplicación
val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

class EstadoDataStore(private val context: Context) {

    private val ESTADO_ACTIVADO = booleanPreferencesKey(name = "modo_activado")

    // Guardar el estado
    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferencias ->
            preferencias[ESTADO_ACTIVADO] = valor
        }
    }

    // Obtener el estado como Flow reactivo
    fun obtenerEstado(): Flow<Boolean?> {
        return context.dataStore.data.map { preferencias ->
            preferencias[ESTADO_ACTIVADO]
        }
    }
}