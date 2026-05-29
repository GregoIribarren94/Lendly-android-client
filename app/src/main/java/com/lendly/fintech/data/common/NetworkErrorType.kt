package com.lendly.fintech.data.common

/**
 * Categoriza los errores de red para que la capa de UI pueda decidir cómo reaccionar
 * (mostrar un Snackbar, redirigir a Login, etc.) sin tener que interpretar strings.
 *
 * Cada tipo expone un [userMessage] claro y NO técnico. Esta es la única fuente de
 * verdad de los mensajes de error de red: si hay que cambiar un texto, se cambia acá.
 */
enum class NetworkErrorType(val userMessage: String) {
    /** Sin internet / no se pudo alcanzar el servidor ([java.io.IOException]). */
    NO_CONNECTION("Sin conexión"),

    /** El servidor tardó demasiado en responder ([java.net.SocketTimeoutException]). */
    TIMEOUT("El servidor tarda en responder"),

    /** Sesión inválida o expirada (HTTP 401). La UI debe redirigir a Login. */
    UNAUTHORIZED("Tu sesión expiró. Iniciá sesión nuevamente."),

    /** Otros errores del cliente (HTTP 4xx). */
    CLIENT_ERROR("Hubo un problema con tu solicitud. Revisá los datos e intentá de nuevo."),

    /** Errores del servidor (HTTP 5xx). */
    SERVER_ERROR("Hubo un problema, intentá más tarde"),

    /** Cualquier otro error inesperado. */
    UNKNOWN("Ocurrió un error inesperado. Intentá nuevamente."),
}