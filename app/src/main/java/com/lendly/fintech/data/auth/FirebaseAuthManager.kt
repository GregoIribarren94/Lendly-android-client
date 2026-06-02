package com.lendly.fintech.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Maneja la sesión de Firebase de forma independiente al token propio de la app.
 *
 * El login propio (token de backend) y la sesión de Firebase corren en paralelo: si Firebase falla,
 * el login de la app no se cae (best-effort). El logout sí cierra ambas.
 */
@Singleton
class FirebaseAuthManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {

    /** Inicia sesión anónima en Firebase si todavía no hay una activa. No lanza si falla. */
    suspend fun ensureSignedIn() {
        if (firebaseAuth.currentUser != null) return
        runCatching { firebaseAuth.signInAnonymously().await() }
    }

    /** Cierra la sesión de Firebase (sincrónico). */
    fun signOut() {
        firebaseAuth.signOut()
    }
}
