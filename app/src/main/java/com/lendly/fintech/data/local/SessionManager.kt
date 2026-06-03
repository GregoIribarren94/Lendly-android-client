package com.lendly.fintech.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Único DataStore de sesión a nivel de proceso.
private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

/**
 * Almacena la sesión del usuario (el token devuelto por `POST /auth/login`) en DataStore.
 *
 * Es la única fuente de verdad del token: el login lo persiste con [saveToken], el resto
 * de la app puede observarlo con [tokenFlow] y el logout lo borra con [clear].
 */
class SessionManager(private val context: Context) {

    /** Token de sesión persistido, o `null` si no hay sesión activa. */
    val tokenFlow: Flow<String?> = context.sessionDataStore.data
        .map { prefs -> prefs[KEY_TOKEN] }

    /** Guarda el token de sesión. */
    suspend fun saveToken(token: String) {
        context.sessionDataStore.edit { prefs -> prefs[KEY_TOKEN] = token }
    }

    /** Borra la sesión (logout). */
    suspend fun clear() {
        context.sessionDataStore.edit { prefs -> prefs.remove(KEY_TOKEN) }
    }

    private companion object {
        val KEY_TOKEN = stringPreferencesKey("auth_token")
    }
}