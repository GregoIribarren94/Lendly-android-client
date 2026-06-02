package com.lendly.fintech.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Préstamo cacheado localmente en Room.
 *
 * El estado se guarda como String (nombre del enum) para no romper ante valores nuevos.
 */
@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val installments: Int,
    val status: String,
    val dueDate: String,
    val interestRate: Double?,
)
