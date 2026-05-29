package com.lendly.fintech.data.common

import retrofit2.HttpException
import java.io.IOException

/**
 * Envuelve una llamada a la API y traduce las excepciones a [Resource.Error],
 * centralizando el manejo de errores de red para todos los repositorios.
 *
 * @param apiCall bloque `suspend` que ejecuta la petición Retrofit.
 * @return [Resource.Success] con los datos o [Resource.Error] con un mensaje
 * legible en español.
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> =
    try {
        Resource.Success(apiCall())
    } catch (e: HttpException) {
        Resource.Error("Error ${e.code()}: ${e.message()}", e)
    } catch (e: IOException) {
        Resource.Error("Sin conexión. Verificá tu internet.", e)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Ocurrió un error inesperado.", e)
    }