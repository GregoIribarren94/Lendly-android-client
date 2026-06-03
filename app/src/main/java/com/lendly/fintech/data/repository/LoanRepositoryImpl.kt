package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.LoansApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.local.db.dao.LoanDao
import com.lendly.fintech.data.local.db.toDomain
import com.lendly.fintech.data.local.db.toEntity
import com.lendly.fintech.data.model.Loan
import com.lendly.fintech.data.model.LoanApplyRequest
import com.lendly.fintech.data.model.LoanResponse

/**
 * Implementación de [LoanRepository] con cache local en Room.
 *
 * [getLoans] pide a la API y refresca el cache; si la red falla, devuelve los préstamos cacheados
 * (offline) y solo propaga el error cuando el cache está vacío. [applyLoan] sigue siendo solo red.
 */
class LoanRepositoryImpl(
    private val api: LoansApi,
    private val loanDao: LoanDao,
) : LoanRepository {

    override suspend fun getLoans(): Resource<List<Loan>> =
        when (val net = safeApiCall { api.getLoans() }) {
            is Resource.Success -> {
                loanDao.replaceAll(net.data.map { it.toEntity() })
                net
            }
            is Resource.Error -> {
                val cached = loanDao.getAll().map { it.toDomain() }
                if (cached.isNotEmpty()) Resource.Success(cached) else net
            }
            Resource.Loading -> net
        }

    override suspend fun applyLoan(amount: Double, installments: Int): Resource<LoanResponse> =
        safeApiCall { api.applyLoan(LoanApplyRequest(amount, installments)) }
}