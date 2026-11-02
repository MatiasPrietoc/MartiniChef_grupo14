package com.example.martinichef_grupo14.data

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.martinichef_grupo14.model.PerfilGuardado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val Context.usuarioDataStore by preferencesDataStore(
    name = "usuario_preferences"
)

class UsuarioDataStore(context: Context) {

    private val appContext = context.applicationContext

    private companion object {
        val NOMBRE = stringPreferencesKey("nombre")
        val EMAIL = stringPreferencesKey("email")
        val DIRECCION = stringPreferencesKey("direccion")
        val PASSWORD_HASH = stringPreferencesKey("password_hash")
        val PASSWORD_SALT = stringPreferencesKey("password_salt")

        const val ITERACIONES = 120_000
    }

    val perfilFlow: Flow<PerfilGuardado?> =
        appContext.usuarioDataStore.data.map { preferences ->
            val email = preferences[EMAIL]

            if (email == null) {
                null
            } else {
                PerfilGuardado(
                    nombre = preferences[NOMBRE].orEmpty(),
                    email = email,
                    direccion = preferences[DIRECCION].orEmpty()
                )
            }
        }

    suspend fun guardarPerfil(
        nombre: String,
        email: String,
        direccion: String,
        password: String? = null
    ) {
        // Si se proporciona contraseña, genera una nueva derivación.
        val credenciales = password?.let {
            withContext(Dispatchers.Default) {
                val salt = ByteArray(16)
                SecureRandom().nextBytes(salt)

                val hash = derivarPassword(it, salt)

                Pair(
                    Base64.encodeToString(salt, Base64.NO_WRAP),
                    Base64.encodeToString(hash, Base64.NO_WRAP)
                )
            }
        }

        // El perfil y las credenciales se guardan juntos.
        appContext.usuarioDataStore.edit { preferences ->
            preferences[NOMBRE] = nombre.trim()
            preferences[EMAIL] = email.trim()
            preferences[DIRECCION] = direccion.trim()

            if (credenciales != null) {
                preferences[PASSWORD_SALT] = credenciales.first
                preferences[PASSWORD_HASH] = credenciales.second
            }
        }
    }

    suspend fun tieneCuentaRegistrada(): Boolean {
        val preferences = appContext.usuarioDataStore.data.first()

        return !preferences[EMAIL].isNullOrBlank() &&
                !preferences[PASSWORD_HASH].isNullOrBlank() &&
                !preferences[PASSWORD_SALT].isNullOrBlank()
    }

    suspend fun verificarCredenciales(
        email: String,
        password: String
    ): Boolean {
        val preferences = appContext.usuarioDataStore.data.first()

        val emailGuardado = preferences[EMAIL] ?: return false
        val hashTexto = preferences[PASSWORD_HASH] ?: return false
        val saltTexto = preferences[PASSWORD_SALT] ?: return false

        if (!email.trim().equals(emailGuardado, ignoreCase = true)) {
            return false
        }

        return withContext(Dispatchers.Default) {
            val salt = Base64.decode(saltTexto, Base64.NO_WRAP)
            val hashGuardado = Base64.decode(hashTexto, Base64.NO_WRAP)
            val hashIngresado = derivarPassword(password, salt)

            MessageDigest.isEqual(hashGuardado, hashIngresado)
        }
    }

    private fun derivarPassword(
        password: String,
        salt: ByteArray
    ): ByteArray {
        val especificacion = PBEKeySpec(
            password.toCharArray(),
            salt,
            ITERACIONES,
            256
        )

        return try {
            SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(especificacion)
                .encoded
        } finally {
            especificacion.clearPassword()
        }
    }
}