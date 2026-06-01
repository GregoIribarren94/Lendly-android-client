package com.lendly.fintech.ui.screens.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.Transaction
import com.lendly.fintech.data.repository.TransactionRepository
import com.lendly.fintech.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TxDetailsUiState {
    data object Loading : TxDetailsUiState()
    data class Success(val transaction: Transaction) : TxDetailsUiState()
    data object NotFound : TxDetailsUiState()
    data class Error(val message: String) : TxDetailsUiState()
}

/**
 * Obtiene el detalle de una transacción por id.
 *
 * La API sólo expone `GET /transactions` (lista completa), no `/transactions/{id}`,
 * así que pedimos `getAll()` y filtramos por id en cliente. Distinguimos "no encontrada"
 * (id inexistente en la lista) de un error de red real.
 */
@HiltViewModel
class TxDetailsViewModel @Inject constructor(
    private val repo: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val txId: String = savedStateHandle.get<String>(Routes.ARG_ID).orEmpty()

    private val _uiState = MutableStateFlow<TxDetailsUiState>(TxDetailsUiState.Loading)
    val uiState: StateFlow<TxDetailsUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = TxDetailsUiState.Loading
            _uiState.value = when (val result = repo.getAll()) {
                is Resource.Success -> {
                    val tx = result.data.find { it.id == txId }
                    if (tx != null) TxDetailsUiState.Success(tx) else TxDetailsUiState.NotFound
                }
                is Resource.Error -> TxDetailsUiState.Error(result.message)
                is Resource.Loading -> TxDetailsUiState.Loading
            }
        }
    }
}
