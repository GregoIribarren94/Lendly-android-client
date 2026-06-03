package com.lendly.fintech.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val currency: String = "PHP",
    val image: String? = null,
    @SerializedName("monthlyInstallment")
    val installments: Int,
    @SerializedName("installmentMonths")
    val installmentMonths: Int,
    val interestRate: Double = 0.0,
    val isFeatured: Boolean = false,
    val isAvailable: Boolean = true,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val description: String? = null,
)

data class ProductsResponse(
    val success: Boolean,
    val products: List<Product>,
    val featured: List<Product> = emptyList(),
    val categories: List<ProductCategory> = emptyList(),
    val brands: List<ProductBrand> = emptyList(),
)

data class ProductCategory(
    val id: String,
    val name: String,
    val icon: String,
    val productCount: Int = 0,
)

data class ProductBrand(
    val id: String,
    val name: String,
    val logo: String? = null,
)