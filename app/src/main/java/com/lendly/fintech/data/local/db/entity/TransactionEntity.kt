package com.lendly.fintech.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Movimiento del historial cacheado localmente en Room.
 *
 * El tipo se guarda como String nullable (nombre del enum) para tolerar tipos no contemplados.
 */
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