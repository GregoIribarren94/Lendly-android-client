package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

/**
 * Movimiento del historial de transacciones.
 *
 * Se deserializa desde `GET /transactions`.
 */
data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    /** Fecha del movimiento en formato ISO (String). */
    val date: String,
    val description: String,
)

/** Tipo de movimiento registrado. */
enum class TransactionType {
    @SerializedName("payment") PAYMENT,
    @SerializedName("loan") LOAN,
    @SerializedName("purchase") PURCHASE,
    @SerializedName("deposit") DEPOSIT,
}