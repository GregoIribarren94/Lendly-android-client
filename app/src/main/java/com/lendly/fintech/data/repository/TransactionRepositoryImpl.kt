package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.TransactionsApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.local.db.dao.TransactionDao
import com.lendly.fintech.data.local.db.toDomain
import com.lendly.fintech.data.local.db.toEntity
import com.lendly.fintech.data.model.Transaction

/**
 * Implementación de [TransactionRepository] con cache local en Room.
 *
 * Pide los movimientos a la API y refresca el cache; si la red falla, devuelve los movimientos
 * cacheados (offline) y solo propaga el error cuando el cache está vacío.
 */
class TransactionRepositoryImpl(
    private val api: TransactionsApi,
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override suspend fun getAll(): Resource<List<Transaction>> =
        when (val net = safeApiCall { api.getTransactions().transactions }) {
            is Resource.Success -> {
                transactionDao.replaceAll(net.data.map { it.toEntity() })
                net
            }
            is Resource.Error -> {
                val cached = transactionDao.getAll().map { it.toDomain() }
                if (cached.isNotEmpty()) Resource.Success(cached) else net
            }
            Resource.Loading -> net
        }
}