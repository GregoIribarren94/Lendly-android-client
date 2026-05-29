package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.LoansApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.model.Loan
import com.lendly.fintech.data.model.LoanApplyRequest
import com.lendly.fintech.data.model.LoanResponse

/**
 * Implementación de [LoanRepository] sobre [LoansApi].
 */
class LoanRepositoryImpl(private val api: LoansApi) : LoanRepository {

    override suspend fun getLoans(): Resource<List<Loan>> =
        safeApiCall { api.getLoans() }

    override suspend fun applyLoan(amount: Double, installments: Int): Resource<LoanResponse> =
        safeApiCall { api.applyLoan(LoanApplyRequest(amount, installments)) }
}