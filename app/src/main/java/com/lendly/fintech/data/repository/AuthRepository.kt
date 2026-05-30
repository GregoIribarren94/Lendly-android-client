package com.lendly.fintech.data.repository

import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.LoginResponse
import com.lendly.fintech.data.model.UserResponse

/**
 * Abstrae el acceso a los endpoints de autenticación.
 */
interface AuthRepository {

    /** Inicia sesión con email y contraseña. */
    suspend fun login(email: String, password: String): Resource<LoginResponse>

    /** Registra un nuevo usuario. */
    suspend fun register(
        name: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
    ): Resource<UserResponse>
}