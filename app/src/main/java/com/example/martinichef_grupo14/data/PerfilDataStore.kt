package com.example.martinichef_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.perfilDataStore by preferencesDataStore(
    name = "perfil_preferences"
)

class PerfilDataStore(context: Context) {

    private val appContext = context.applicationContext

    private companion object {
        val ARCHIVO_FOTO = stringPreferencesKey("archivo_foto")
    }

    val archivoFotoFlow: Flow<String?> =
        appContext.perfilDataStore.data.map { preferences ->
            preferences[ARCHIVO_FOTO]
        }

    suspend fun guardarNombreArchivo(nombre: String) {
        appContext.perfilDataStore.edit { preferences ->
            preferences[ARCHIVO_FOTO] = nombre
        }
    }
}