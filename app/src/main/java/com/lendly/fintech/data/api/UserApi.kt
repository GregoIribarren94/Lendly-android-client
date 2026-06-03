package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.UpdateUserRequest
import com.lendly.fintech.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Endpoints relacionados con el usuario.
 */
interface UserApi {

    /** Obtiene los datos del usuario por su identificador. */
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): User

    /** Actualiza los datos del usuario. */
    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body request: UpdateUserRequest): User
}