package com.lendly.fintech.data.repository

import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Product

/**
 * Abstrae el acceso al catálogo de productos del Shop.
 */
interface ProductRepository {

    /** Devuelve el catálogo de productos disponibles. */
    suspend fun getAll(): Resource<List<Product>>
}