package com.lendly.fintech.data.repository

import com.lendly.fintech.data.api.ProductsApi
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.common.safeApiCall
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.data.model.ProductsResponse
class ProductRepositoryImpl(private val api: ProductsApi) : ProductRepository {

    override suspend fun getAll(): Resource<List<Product>> =
        safeApiCall { api.getProducts().products }
}