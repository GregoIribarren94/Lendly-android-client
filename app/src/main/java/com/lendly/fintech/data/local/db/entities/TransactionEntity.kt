package com.lendly.fintech.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val type: String?,
    val title: String,
    val description: String,
    val amount: Double,
    val currency: String,
    val status: String,
    val date: String,
    val loanId: String?,
    val referenceNumber: String?,
)
