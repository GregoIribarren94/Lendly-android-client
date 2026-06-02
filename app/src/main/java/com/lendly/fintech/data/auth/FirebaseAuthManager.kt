package com.lendly.fintech.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Maneja la sesión de Firebase Auth que corre "aparte" del login contra el mock.
 *
 * Tras un login exitoso, [ensureSignedIn] abre una sesión anónima en Firebase (sin OTP visible)
 * para tener un usuario de Firebase asociado. Es best-effort: si falla (p. ej. el proveedor
 * Anonymous no está habilitado en la consola), no debe bloquear el login de la app.
 *
 * Requisito de consola: habilitar el proveedor **Anonymous** en Firebase Authentication.
 */
@Singleton
class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth,
) {

    /** UID de la sesión de Firebase actual, o `null` si no hay. */
    val currentUid: String?
        get() = auth.currentUser?.uid

    /**
     * Garantiza que haya una sesión de Firebase. Si ya existe, la reutiliza; si no, inicia
     * sesión anónima. Devuelve el UID en caso de éxito.
     */
    suspend fun ensureSignedIn(): Result<String> = runCatching {
        val user = auth.currentUser ?: auth.signInAnonymously().await().user
        user?.uid ?: error("No se pudo iniciar sesión en Firebase")
    }

    /** Cierra la sesión de Firebase (logout). */
    fun signOut() {
        auth.signOut()
    }
}