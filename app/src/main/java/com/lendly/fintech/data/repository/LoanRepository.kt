package com.lendly.fintech.data.repository

import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Loan
import com.lendly.fintech.data.model.LoanResponse

/**
 * Abstrae el acceso a los endpoints de préstamos.
 */
interface LoanRepository {

    /** Devuelve los préstamos del usuario (activos e historial). */
    suspend fun getLoans(): Resource<List<Loan>>

    /** Solicita un nuevo préstamo. */
    suspend fun applyLoan(amount: Double, installments: Int): Resource<LoanResponse>
}