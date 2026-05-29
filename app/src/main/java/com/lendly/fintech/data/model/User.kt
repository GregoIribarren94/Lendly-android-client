package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de dominio del usuario autenticado.
 *
 * Se deserializa desde `GET /users/{id}` y se usa también directamente en la UI.
 */
data class User(
    val id: String,
    val name: String,
    @SerializedName("lastName") val lastName: String,
    val email: String,
    val phone: String,
    /** Puntaje crediticio mostrado en el gauge/barra del perfil. */
    @SerializedName("creditScore") val creditScore: Int,
    /** URL de la imagen de perfil; puede no estar presente. */
    @SerializedName("profileImage") val profileImage: String? = null,
)