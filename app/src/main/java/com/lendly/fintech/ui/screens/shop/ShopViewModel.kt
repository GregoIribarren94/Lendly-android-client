package com.lendly.fintech.ui.screens.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

// ── UI State ──────────────────────────────────────────────────────────────────

sealed class ShopUiState {
    data object Loading : ShopUiState()
    data class Success(val products: List<Product>) : ShopUiState()
    data class Error(val message: String) : ShopUiState()
}

// ── ViewModel ─────────────────────────────────────────────────────────────────

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    // ── Todos los productos de la API (sin filtrar) ──────────────────────────
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    // ── Filtro activo ────────────────────────────────────────────────────────
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    // ── Estado de la UI (productos ya filtrados) ─────────────────────────────
    private val _uiState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        // Cada vez que cambian los productos O el filtro, recalculamos la UI
        viewModelScope.launch {
            combine(_allProducts, _filterState) { products, filter ->
                if (products.isEmpty()) return@combine
                _uiState.value = ShopUiState.Success(applyFilter(products, filter))
            }.collect {}
        }
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ShopUiState.Loading
            when (val result = productRepository.getAll()) {
                is Resource.Success -> {
                    _allProducts.value = result.data
                    _uiState.value = ShopUiState.Success(
                        applyFilter(result.data, _filterState.value)
                    )
                }
                is Resource.Error   -> _uiState.value = ShopUiState.Error(result.message)
                is Resource.Loading -> _uiState.value = ShopUiState.Loading
            }
        }
    }

    // ── Llamado desde MainNavHost cuando el usuario aprieta Apply ────────────
    fun applyFilter(filter: FilterState) {
        _filterState.value = filter
        val products = _allProducts.value
        if (products.isNotEmpty()) {
            _uiState.value = ShopUiState.Success(applyFilter(products, filter))
        }
    }

    fun resetFilter() {
        applyFilter(FilterState())
    }

    // ── Lógica de filtrado ───────────────────────────────────────────────────
    private fun applyFilter(products: List<Product>, filter: FilterState): List<Product> {
        var result = products

        // Marca
        if (filter.selectedBrand != "All") {
            result = result.filter { it.brand.equals(filter.selectedBrand, ignoreCase = true) }
        }

        // Rango de precio
        result = when (filter.selectedPriceRange) {
            "\$500 - \$1000"  -> result.filter { it.price in 500.0..1000.0 }
            "\$1000 - \$5000" -> result.filter { it.price in 1000.0..5000.0 }
            else              -> result
        }

        // Ordenamiento
        result = when (filter.selectedSort) {
            "Popular"      -> result.sortedByDescending { it.price }
            "Low Interest" -> result.sortedBy { it.price }
            else           -> result // "Most Recent" → orden original de la API
        }

        return result
    }

    // ── Helpers para la UI ───────────────────────────────────────────────────

    fun recommended(products: List<Product>, limit: Int = 6): List<Product> =
        products.take(limit)

    fun bestSellers(products: List<Product>, limit: Int = 6): List<Product> =
        products.takeLast(limit.coerceAtMost(products.size))

    fun categories(products: List<Product>): List<String> =
        products.map { it.category }.distinct()

    fun brands(products: List<Product>): List<String> =
        products.map { it.brand }.distinct()
}