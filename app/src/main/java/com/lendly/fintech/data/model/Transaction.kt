package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

/**
 * Movimiento del historial. Se deserializa desde el array `transactions`
 * de `GET /transactions` (ver TransactionsResponse).
 */
data class Transaction(
    val id: String,
    /** Nullable: el backend puede devolver tipos aún no contemplados (24 txns totales). */
    val type: TransactionType?,
    /** Incluye el comercio: "Loan Payment — Nike Inc." */
    val title: String,
    /** Detalle del movimiento: "Monthly installment payment". */
    val description: String,
    /** Con signo: negativo = egreso, positivo = ingreso. */
    val amount: Double,
    val currency: String,
    /** "COMPLETED", etc. String para no romper ante valores nuevos. */
    val status: String,
    /** Fecha ISO-8601 UTC con Z: "2026-05-01T10:30:00Z". */
    val date: String,
    val loanId: String? = null,
    val referenceNumber: String? = null,
)

/** Tipo de movimiento. */
enum class TransactionType {
    @SerializedName("LOAN_PAYMENT")      LOAN_PAYMENT,
    @SerializedName("CASH_IN")           CASH_IN,
    @SerializedName("LOAN_DISBURSEMENT") LOAN_DISBURSEMENT,
}
