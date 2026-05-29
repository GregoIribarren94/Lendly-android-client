package com.lendly.fintech.data.remote

import com.lendly.fintech.core.SessionEventBus
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

/**
 * Interceptor que maneja el 401 (no autorizado) de forma global: ante cualquier
 * respuesta con código 401 notifica al [SessionEventBus] para que la UI redirija
 * a Login, sin importar qué pantalla originó la llamada.
 */
class AuthInterceptor(
    private val sessionEventBus: SessionEventBus,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            sessionEventBus.notifySessionExpired()
        }

        return response
    }
}