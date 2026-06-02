package com.lendly.fintech.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

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
