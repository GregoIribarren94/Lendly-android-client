package com.lendly.fintech.data.api

import com.lendly.fintech.data.model.ProductsResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse
}