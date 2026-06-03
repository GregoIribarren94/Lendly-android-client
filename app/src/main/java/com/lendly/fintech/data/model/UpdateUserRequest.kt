package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    val name: String,
    @SerializedName("lastName") val lastName: String,
    val birthDate: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val phone: String,
)
