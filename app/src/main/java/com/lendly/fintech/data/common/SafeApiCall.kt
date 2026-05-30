package com.lendly.fintech.data.common

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Envuelve una llamada a la API y traduce las excepciones a [Resource.Error],
 * centralizando el manejo de errores de red para todos los repositorios.
 *
 * Clasifica cada error en un [NetworkErrorType] con un mensaje claro y NO técnico,
 * para que la UI pueda reaccionar de forma consistente:
 * - [NetworkErrorType.NO_CONNECTION] → Snackbar "Sin conexión"
 * - [NetworkErrorType.TIMEOUT] → "El servidor tarda en responder"
 * - [NetworkErrorType.UNAUTHORIZED] (401) → redirigir a Login
 * - [NetworkErrorType.CLIENT_ERROR] (4xx) → mensaje claro al usuario
 * - [NetworkErrorType.SERVER_ERROR] (5xx) → "Hubo un problema, intentá más tarde"
 *
 * @param apiCall bloque `suspend` que ejecuta la petición Retrofit.
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> =
    try {
        Resource.Success(apiCall())
    } catch (e: SocketTimeoutException) {
        error(NetworkErrorType.TIMEOUT, e)
    } catch (e: IOException) {
        error(NetworkErrorType.NO_CONNECTION, e)
    } catch (e: HttpException) {
        val type = when (e.code()) {
            401 -> NetworkErrorType.UNAUTHORIZED
            in 400..499 -> NetworkErrorType.CLIENT_ERROR
            in 500..599 -> NetworkErrorType.SERVER_ERROR
            else -> NetworkErrorType.UNKNOWN
        }
        error(type, e)
    } catch (e: Exception) {
        error(NetworkErrorType.UNKNOWN, e)
    }

private fun error(type: NetworkErrorType, throwable: Throwable): Resource.Error =
    Resource.Error(message = type.userMessage, type = type, throwable = throwable)