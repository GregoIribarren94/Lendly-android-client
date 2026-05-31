package com.lendly.fintech.ui.screens.shop

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FilterState(
    val selectedBrand:      String = "All",
    val selectedGender:     String = "All",
    val selectedSort:       String = "Most Recent",
    val selectedPriceRange: String = "All",
)

class FilterViewModel : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    fun selectBrand(brand: String) {
        _state.value = _state.value.copy(selectedBrand = brand)
    }

    fun selectGender(gender: String) {
        _state.value = _state.value.copy(selectedGender = gender)
    }

    fun selectSort(sort: String) {
        _state.value = _state.value.copy(selectedSort = sort)
    }

    fun selectPriceRange(range: String) {
        _state.value = _state.value.copy(selectedPriceRange = range)
    }

    fun reset() {
        _state.value = FilterState()
    }

    fun apply() {
        // Acá se puede exponer el estado al ShopViewModel cuando esté conectado al repo
    }
}