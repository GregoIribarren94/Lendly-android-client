package com.lendly.fintech.ui.screens.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    // Productos reales de la API
    private var allProducts: List<Product> = emptyList()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _results = MutableStateFlow<List<Product>>(emptyList())
    val results: StateFlow<List<Product>> = _results.asStateFlow()

    private val _recentSearches = MutableStateFlow(
        listOf("iPhone", "Nike", "Sony", "Samsung")
    )
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    init {
        loadProducts()

        _query
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { q -> _results.value = filter(q) }
            .launchIn(viewModelScope)
    }

    private fun loadProducts() {
        viewModelScope.launch {
            when (val result = productRepository.getAll()) {
                is Resource.Success -> allProducts = result.data
                else -> Unit
            }
        }
    }

    fun onQueryChange(newQuery: String) { _query.value = newQuery }
    fun clearQuery() { _query.value = "" }

    fun saveRecent(q: String) {
        if (q.isBlank()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(q)
        current.add(0, q)
        _recentSearches.value = current.take(10)
    }

    fun removeRecent(item: String) {
        _recentSearches.value = _recentSearches.value.filter { it != item }
    }

    fun clearAllRecents() { _recentSearches.value = emptyList() }

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