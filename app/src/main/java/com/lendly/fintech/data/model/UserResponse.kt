package com.lendly.fintech.data.model

/**
 * Respuesta de `POST /auth/create`: token de sesión y los datos
 * del usuario recién creado.
 */
data class UserResponse(
    val token: String,
    val user: User,
)