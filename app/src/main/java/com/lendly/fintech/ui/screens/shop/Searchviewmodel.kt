package com.lendly.fintech.ui.screens.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.model.Product
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {

    // ── Catálogo completo ────────────────────────────────────────────────────
    private val allProducts: List<Product> = listOf(
        Product("1",  "iPhone 14 Pro",       999.0,  null, "Phone",      "Apple",   12),
        Product("2",  "Samsung Galaxy S23",   849.0,  null, "Phone",      "Samsung", 12),
        Product("3",  "Xiaomi 13T",           499.0,  null, "Phone",      "Xiaomi",   6),
        Product("4",  "Sony WH-1000XM5",      349.0,  null, "Headphones", "Sony",     6),
        Product("5",  "AirPods Pro 2",        249.0,  null, "Headphones", "Apple",    3),
        Product("6",  "Jabra Elite 85h",      179.0,  null, "Headphones", "Jabra",    3),
        Product("7",  "MacBook Air M2",      1199.0,  null, "Apparel",    "Apple",   24),
        Product("8",  "Dell XPS 15",          999.0,  null, "Apparel",    "Dell",    18),
        Product("9",  "Nike Air Max 270",     120.0,  null, "Shoes",      "Nike",     3),
        Product("10", "Adidas Ultra Boost",   130.0,  null, "Shoes",      "Adidas",   3),
        Product("11", "Blue shirt",            49.0,  null, "Apparel",    "Zara",     1),
        Product("12", "Red shirt",             49.0,  null, "Apparel",    "Zara",     1),
        Product("13", "Yellow shirt",          49.0,  null, "Apparel",    "H&M",      1),
        Product("14", "Blue Shoes",            89.0,  null, "Shoes",      "Nike",     3),
        Product("15", "Yellow Shoes",          89.0,  null, "Shoes",      "Adidas",   3),
    )

    // ── Query ────────────────────────────────────────────────────────────────
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // ── Resultados filtrados ─────────────────────────────────────────────────
    private val _results = MutableStateFlow<List<Product>>(emptyList())
    val results: StateFlow<List<Product>> = _results.asStateFlow()

    // ── Búsquedas recientes ──────────────────────────────────────────────────
    private val _recentSearches = MutableStateFlow(
        listOf(
            "Blue shirt", "Red shirt", "Yellow shirt",
            "Blue Shoes", "Yellow Shoes", "Red Shoes",
        )
    )
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    init {
        _query
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { q -> _results.value = filter(q) }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun clearQuery() {
        _query.value = ""
    }

    // Guarda la búsqueda en recientes al navegar a un producto
    fun saveRecent(q: String) {
        if (q.isBlank()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(q)          // evita duplicados
        current.add(0, q)          // agrega al principio
        _recentSearches.value = current.take(10) // máximo 10
    }

    fun removeRecent(item: String) {
        _recentSearches.value = _recentSearches.value.filter { it != item }
    }

    fun clearAllRecents() {
        _recentSearches.value = emptyList()
    }

    private fun filter(q: String): List<Product> {
        if (q.isBlank()) return emptyList()
        val lower = q.lowercase()
        return allProducts.filter { p ->
            p.name.lowercase().contains(lower) ||
                    p.category.lowercase().contains(lower) ||
                    p.brand.lowercase().contains(lower)
        }
    }
}