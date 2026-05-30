package com.lendly.fintech.data.model

/**
 * Producto del catálogo del Shop.
 *
 * Se deserializa desde `GET /products`.
 */
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    /** URL de la imagen del producto; puede no estar presente. */
    val image: String? = null,
    val category: String,
    val brand: String,
    /** Cantidad de cuotas disponibles para la compra. */
    val installments: Int,
)