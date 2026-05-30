package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

/**
 * Payload de registro de usuario para `POST /auth/create`.
 */
data class CreateUserRequest(
    val name: String,
    @SerializedName("lastName") val lastName: String,
    val email: String,
    val phone: String,
    val password: String,
)