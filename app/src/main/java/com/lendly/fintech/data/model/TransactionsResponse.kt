package com.lendly.fintech.data.model

/**
 * Respuesta de `GET /transactions`: lista paginada de movimientos.
 */
data class TransactionsResponse(
    val success: Boolean,
    val pagination: Pagination,
    val transactions: List<Transaction>,
)

data class Pagination(
    val page: Int,
    val limit: Int,
    val total: Int,
    val hasNextPage: Boolean,
)
