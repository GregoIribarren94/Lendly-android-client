package com.lendly.fintech.data.common

/**
 * Estado de una operación contra la API.
 *
 * - [Loading]: operación en curso (lo emite normalmente el ViewModel antes de
 *   invocar al repositorio).
 * - [Success]: la operación finalizó correctamente y trae los datos.
 * - [Error]: la operación falló; incluye un mensaje legible, el [type] de error
 *   (para que la UI decida cómo reaccionar) y, opcionalmente, la excepción original.
 */
sealed class Resource<out T> {

    data object Loading : Resource<Nothing>()

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(
        val message: String,
        val type: NetworkErrorType = NetworkErrorType.UNKNOWN,
        val throwable: Throwable? = null,
    ) : Resource<Nothing>()
}