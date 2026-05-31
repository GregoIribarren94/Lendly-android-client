package com.lendly.fintech.data.remote

import com.lendly.fintech.core.SessionEventBus
import com.lendly.fintech.data.local.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

/**
 * Interceptor de autenticación:
 * - Adjunta el token de sesión como header `Authorization: Bearer <token>` cuando existe.
 * - Maneja el 401 (no autorizado) de forma global: ante cualquier respuesta 401 notifica
 *   al [SessionEventBus] para que la UI redirija a Login.
 */
class AuthInterceptor(
    private val sessionEventBus: SessionEventBus,
    private val sessionManager: SessionManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { sessionManager.tokenFlow.first() }

        val request = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            sessionEventBus.notifySessionExpired()
        }

        return response
    }
}
