package com.lendly.fintech.data.repository

import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Transaction

/**
 * Abstrae el acceso al historial de transacciones.
 */
interface TransactionRepository {

    /** Devuelve el historial de movimientos del usuario. */
    suspend fun getAll(): Resource<List<Transaction>>
}