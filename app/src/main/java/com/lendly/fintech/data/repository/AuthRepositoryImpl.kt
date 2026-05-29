package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.AuthApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.model.CreateUserRequest
import com.lendly.fintech.data.model.LoginRequest
import com.lendly.fintech.data.model.LoginResponse
import com.lendly.fintech.data.model.UserResponse

/**
 * Implementación de [AuthRepository] sobre [AuthApi].
 */
class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> =
        safeApiCall { api.login(LoginRequest(email, password)) }

    override suspend fun register(
        name: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
    ): Resource<UserResponse> =
        safeApiCall { api.create(CreateUserRequest(name, lastName, email, phone, password)) }
}