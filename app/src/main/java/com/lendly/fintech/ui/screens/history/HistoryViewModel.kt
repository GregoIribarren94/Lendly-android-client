package com.lendly.fintech.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Transaction
import com.lendly.fintech.data.model.TransactionType
import com.lendly.fintech.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

enum class HistoryFilter { ALL, LOANS, PAYMENTS, CASH_IN }

data class HistorySection(val header: String, val transactions: List<Transaction>)

sealed class HistoryUiState {
    data object Loading : HistoryUiState()
    data class Success(
        val sections: List<HistorySection>,
        val selectedFilter: HistoryFilter,
        val searchQuery: String,
    ) : HistoryUiState()
    data class Error(val message: String) : HistoryUiState()
}

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: TransactionRepository,
) : ViewModel() {

    private val _allTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    private val _filter = MutableStateFlow(HistoryFilter.ALL)
    private val _searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        // Reactive updates for filter/search changes after initial load.
        // Guard prevents premature Success(empty) before load() completes.
        viewModelScope.launch {
            combine(_allTransactions, _filter, _searchQuery) { txs, filter, query ->
                if (txs.isEmpty()) return@combine
                val filtered = applyFilter(txs, filter, query)
                _uiState.value = HistoryUiState.Success(
                    sections = groupByDate(filtered),
                    selectedFilter = filter,
                    searchQuery = query,
                )
            }.collect {}
        }
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            when (val result = repo.getAll()) {
                is Resource.Success -> {
                    _allTransactions.value = result.data
                    // Set Success directly (mirrors ShopViewModel).
                    // Handles empty API response → EmptyState instead of infinite Loading.
                    _uiState.value = HistoryUiState.Success(
                        sections = groupByDate(applyFilter(result.data, _filter.value, _searchQuery.value)),
                        selectedFilter = _filter.value,
                        searchQuery = _searchQuery.value,
                    )
                }
                is Resource.Error   -> _uiState.value = HistoryUiState.Error(result.message)
                is Resource.Loading -> _uiState.value = HistoryUiState.Loading
            }
        }
    }

    fun onFilterSelected(filter: HistoryFilter) {
        _filter.value = filter
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun applyFilter(
        txs: List<Transaction>,
        filter: HistoryFilter,
        query: String,
    ): List<Transaction> {
        var result = when (filter) {
            HistoryFilter.ALL      -> txs
            HistoryFilter.LOANS    -> txs.filter { it.type == TransactionType.LOAN }
            HistoryFilter.PAYMENTS -> txs.filter { it.type == TransactionType.PAYMENT }
            HistoryFilter.CASH_IN  -> txs.filter { it.type == TransactionType.DEPOSIT }
        }
        if (query.isNotBlank()) {
            result = result.filter { it.description.contains(query, ignoreCase = true) }
        }
        return result
    }

    private fun groupByDate(txs: List<Transaction>): List<HistorySection> {
        val today = LocalDate.now()
        val (todayTxs, olderTxs) = txs.partition { tx ->
            runCatching { LocalDate.parse(tx.date.take(10)) == today }.getOrDefault(false)
        }
        return buildList {
            if (todayTxs.isNotEmpty()) add(HistorySection("Today", todayTxs))
            if (olderTxs.isNotEmpty()) add(HistorySection("Older", olderTxs))
        }
    }
}
