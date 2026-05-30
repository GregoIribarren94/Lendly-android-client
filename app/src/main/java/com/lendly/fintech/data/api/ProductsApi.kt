package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.Product
import retrofit2.http.GET

/**
 * Endpoints del catálogo del Shop.
 */
interface ProductsApi {

    /** Devuelve el catálogo de productos disponibles. */
    @GET("products")
    suspend fun getProducts(): List<Product>
}