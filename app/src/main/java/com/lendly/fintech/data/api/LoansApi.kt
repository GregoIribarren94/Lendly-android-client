package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.Loan
import com.lendly.fintech.data.model.LoanApplyRequest
import com.lendly.fintech.data.model.LoanResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Endpoints de préstamos: listado y solicitud de nuevos préstamos.
 */
interface LoansApi {

    /** Devuelve los préstamos del usuario (activos e historial). */
    @GET("loans")
    suspend fun getLoans(): List<Loan>

    /** Solicita un nuevo préstamo y devuelve el préstamo creado. */
    @POST("loans/apply")
    suspend fun applyLoan(@Body req: LoanApplyRequest): LoanResponse
}