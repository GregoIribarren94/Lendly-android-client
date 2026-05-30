package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

/**
 * Préstamo del usuario, ya sea activo o parte del historial.
 *
 * Se deserializa desde `GET /loans`.
 */
data class Loan(
    val id: String,
    /** Monto total del préstamo. */
    val amount: Double,
    /** Cantidad de cuotas. */
    val installments: Int,
    val status: LoanStatus,
    /** Fecha de vencimiento en formato ISO (String). */
    @SerializedName("dueDate") val dueDate: String,
    /** Tasa de interés; puede no venir en todas las respuestas. */
    @SerializedName("interestRate") val interestRate: Double? = null,
)

/** Estado posible de un préstamo. */
enum class LoanStatus {
    @SerializedName("pending") PENDING,
    @SerializedName("active") ACTIVE,
    @SerializedName("paid") PAID,
    @SerializedName("rejected") REJECTED,
}