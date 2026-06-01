package com.lendly.fintech.ui.screens.shop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class FilterState(
    val selectedBrand:      String = "All",
    val selectedGender:     String = "All",
    val selectedSort:       String = "Most Recent",
    val selectedPriceRange: String = "All",
)

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    fun selectBrand(brand: String)      { _state.value = _state.value.copy(selectedBrand = brand) }
    fun selectGender(gender: String)    { _state.value = _state.value.copy(selectedGender = gender) }
    fun selectSort(sort: String)        { _state.value = _state.value.copy(selectedSort = sort) }
    fun selectPriceRange(range: String) { _state.value = _state.value.copy(selectedPriceRange = range) }

    fun reset() { _state.value = FilterState() }

    fun currentFilter(): FilterState = _state.value
}