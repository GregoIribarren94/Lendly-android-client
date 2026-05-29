package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.CreateUserRequest
import com.lendly.fintech.data.model.LoginRequest
import com.lendly.fintech.data.model.LoginResponse
import com.lendly.fintech.data.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Endpoints de autenticación: inicio de sesión y registro de usuarios.
 */
interface AuthApi {

    /** Inicia sesión y devuelve el token de sesión. */
    @POST("auth/login")
    suspend fun login(@Body req: LoginRequest): LoginResponse

    /** Crea un nuevo usuario y devuelve sus datos junto al token. */
    @POST("auth/create")
    suspend fun create(@Body req: CreateUserRequest): UserResponse
}