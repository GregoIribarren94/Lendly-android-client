package com.lendly.fintech.data.model

/**
 * Payload para solicitar un nuevo préstamo en `POST /loans/apply`.
 */
data class LoanApplyRequest(
    val amount: Double,
    val installments: Int,
)