package com.lendly.fintech.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val installments: Int,
    val status: String,
    val dueDate: String,
    val interestRate: Double?,
)
