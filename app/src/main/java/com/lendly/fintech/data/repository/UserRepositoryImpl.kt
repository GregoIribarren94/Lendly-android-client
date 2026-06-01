package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.UserApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.local.db.dao.UserDao
import com.lendly.fintech.data.local.db.toDomain
import com.lendly.fintech.data.local.db.toEntity
import com.lendly.fintech.data.model.User

/**
 * Implementación de [UserRepository] con cache local en Room.
 *
 * Estrategia: pide a la API; si responde, actualiza el cache y devuelve los datos frescos.
 * Si la red falla, cae al perfil cacheado en Room (acceso offline) y solo propaga el error
 * cuando tampoco hay cache.
 */
class UserRepositoryImpl(
    private val api: UserApi,
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun getProfile(id: String): Resource<User> =
        when (val net = safeApiCall { api.getUser(id) }) {
            is Resource.Success -> {
                userDao.upsert(net.data.toEntity())
                net
            }
            is Resource.Error -> {
                val cached = userDao.getById(id)?.toDomain()
                if (cached != null) Resource.Success(cached) else net
            }
            Resource.Loading -> net
        }
}