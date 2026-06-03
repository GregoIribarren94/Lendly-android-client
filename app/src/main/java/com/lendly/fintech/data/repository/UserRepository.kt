package com.lendly.fintech.data.repository

import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.UpdateUserRequest
import com.lendly.fintech.data.model.User

/**
 * Abstrae el acceso a los datos del usuario.
 */
interface UserRepository {

    /** Obtiene el perfil del usuario por su identificador. */
    suspend fun getProfile(id: String): Resource<User>

    /** Actualiza el perfil del usuario. */
    suspend fun updateProfile(id: String, request: UpdateUserRequest): Resource<User>
}