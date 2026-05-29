package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Endpoints relacionados con el usuario.
 */
interface UserApi {

    /** Obtiene los datos del usuario por su identificador. */
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): User
}