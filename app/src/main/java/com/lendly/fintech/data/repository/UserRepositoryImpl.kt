package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.UserApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.model.User

/**
 * Implementación de [UserRepository] sobre [UserApi].
 */
class UserRepositoryImpl(private val api: UserApi) : UserRepository {

    override suspend fun getProfile(id: String): Resource<User> =
        safeApiCall { api.getUser(id) }
}