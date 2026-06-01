package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.TransactionsResponse
import retrofit2.http.GET

interface TransactionsApi {

    /** Devuelve el historial de movimientos (envuelto en TransactionsResponse). */
    @GET("transactions")
    suspend fun getTransactions(): TransactionsResponse
}
