package com.example.martinichef_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.favoritosDataStore by preferencesDataStore(
    name = "favoritos_preferences"
)

class FavoritosDataStore(
    private val context: Context
) {
    private val favoritosKey = stringSetPreferencesKey("recetas_favoritas")

    val favoritosFlow: Flow<Set<Int>> =
        context.favoritosDataStore.data.map { preferencias ->
            preferencias[favoritosKey]
                .orEmpty()
                .mapNotNull { id -> id.toIntOrNull() }
                .toSet()
        }

    suspend fun guardarFavoritos(idsFavoritos: Set<Int>) {
        context.favoritosDataStore.edit { preferencias ->
            preferencias[favoritosKey] =
                idsFavoritos.map { id -> id.toString() }.toSet()
        }
    }
}
