package com.example.martinichef_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.martinichef_grupo14.model.PreferenciasApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.ajustesDataStore by preferencesDataStore(
    name = "ajustes_preferences"
)

class AjustesDataStore(context: Context) {

    private val appContext = context.applicationContext

    private companion object {
        val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
        val ESCALA_TEXTO = floatPreferencesKey("escala_texto")
    }

    val preferenciasFlow: Flow<PreferenciasApp> =
        appContext.ajustesDataStore.data.map { preferences ->
            PreferenciasApp(
                modoOscuro = preferences[MODO_OSCURO] ?: false,
                escalaTexto = (
                        preferences[ESCALA_TEXTO] ?: 1f
                        ).coerceIn(0.9f, 1.2f)
            )
        }

    suspend fun guardarModoOscuro(activado: Boolean) {
        appContext.ajustesDataStore.edit { preferences ->
            preferences[MODO_OSCURO] = activado
        }
    }

    suspend fun guardarEscalaTexto(escala: Float) {
        appContext.ajustesDataStore.edit { preferences ->
            preferences[ESCALA_TEXTO] =
                escala.coerceIn(0.9f, 1.2f)
        }
    }
}