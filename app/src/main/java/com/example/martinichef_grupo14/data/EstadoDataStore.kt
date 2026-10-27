package com.example.martinichef_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "estado_preferences")

class EstadoDataStore(private val context: Context) {

    companion object {
        val ESTADO_KEY = booleanPreferencesKey("estado_activo")
    }

    val estadoFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[ESTADO_KEY] ?: false
    }

    suspend fun guardarEstado(activo: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ESTADO_KEY] = activo
        }
    }
}