package com.lendly.fintech.data.model

/**
 * Respuesta de `POST /loans/apply`: el préstamo creado y un mensaje
 * opcional del backend.
 */
data class LoanResponse(
    val loan: Loan,
    val message: String? = null,
)