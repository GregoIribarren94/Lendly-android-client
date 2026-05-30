package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.Transaction
import retrofit2.http.GET

/**
 * Endpoints del historial de transacciones.
 */
interface TransactionsApi {

    /** Devuelve el historial de movimientos del usuario. */
    @GET("transactions")
    suspend fun getTransactions(): List<Transaction>
}