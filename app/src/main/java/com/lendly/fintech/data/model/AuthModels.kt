package com.lendly.fintech.data.model

/**
 * Payload de inicio de sesión para `POST /auth/login`.
 */
data class LoginRequest(
    val phone: String,
    val password: String,
)

/**
 * Respuesta de `POST /auth/login`: token de sesión y, opcionalmente,
 * los datos del usuario autenticado.
 */
data class LoginResponse(
    val token: String,
    val user: User? = null,
)