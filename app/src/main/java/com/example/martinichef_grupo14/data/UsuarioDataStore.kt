package com.example.martinichef_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.martinichef_grupo14.model.PerfilGuardado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.usuarioDataStore by preferencesDataStore(
    name = "usuario_preferences"
)

class UsuarioDataStore(context: Context) {

    private val appContext = context.applicationContext

    private companion object {
        val NOMBRE = stringPreferencesKey("nombre")
        val EMAIL = stringPreferencesKey("email")
        val DIRECCION = stringPreferencesKey("direccion")
    }

    val perfilFlow: Flow<PerfilGuardado?> =
        appContext.usuarioDataStore.data.map { preferences ->

            val email = preferences[EMAIL]

            if (email == null) {
                null
            } else {
                PerfilGuardado(
                    nombre = preferences[NOMBRE] ?: "",
                    email = email,
                    direccion = preferences[DIRECCION] ?: ""
                )
            }
        }

    suspend fun guardarPerfil(
        nombre: String,
        email: String,
        direccion: String
    ) {
        appContext.usuarioDataStore.edit { preferences ->
            preferences[NOMBRE] = nombre.trim()
            preferences[EMAIL] = email.trim()
            preferences[DIRECCION] = direccion.trim()
        }
    }
}