package com.lendly.fintech.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Perfil del usuario cacheado localmente en Room.
 *
 * Espejo de [com.lendly.fintech.data.model.User] para persistencia offline tras el login.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val creditScore: Int,
    val profileImage: String?,
)