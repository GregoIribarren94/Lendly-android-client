package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.TransactionsApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.model.Transaction

/**
 * Implementación de [TransactionRepository] sobre [TransactionsApi].
 */
class TransactionRepositoryImpl(private val api: TransactionsApi) : TransactionRepository {

    override suspend fun getAll(): Resource<List<Transaction>> =
        safeApiCall { api.getTransactions() }
}