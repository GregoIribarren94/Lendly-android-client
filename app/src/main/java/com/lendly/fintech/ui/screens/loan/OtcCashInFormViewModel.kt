package com.lendly.fintech.ui.screens.loan

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lendly.fintech.R
import com.lendly.fintech.data.model.OtcPartner
import com.lendly.fintech.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OtcCashInFormUiState(
    val partner: OtcPartner,
    val amount: String = "",
    @StringRes val amountErrorRes: Int? = null,
    val navigateToSuccessWithCode: String? = null,
)

@HiltViewModel
class OtcCashInFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val partner: OtcPartner = runCatching {
        OtcPartner.valueOf(savedStateHandle.get<String>(Routes.ARG_PARTNER_ID).orEmpty())
    }.getOrDefault(OtcPartner.SEVEN_ELEVEN)

    private val _uiState = MutableStateFlow(OtcCashInFormUiState(partner = partner))
    val uiState: StateFlow<OtcCashInFormUiState> = _uiState.asStateFlow()

    fun onAmountChange(value: String) {
        _uiState.update { it.copy(amount = value, amountErrorRes = null) }
    }

    fun onGenerateCode() {
        val amountValue = _uiState.value.amount
        val parsed = amountValue.toDoubleOrNull()
        val errorRes = when {
            amountValue.isBlank() -> R.string.otc_cash_in_form_amount_error_empty
            parsed == null || parsed <= 0.0 -> R.string.otc_cash_in_form_amount_error_zero
            parsed > partner.maxAmount -> R.string.otc_cash_in_form_amount_error_max
            else -> null
        }

        if (errorRes != null) {
            _uiState.update { it.copy(amountErrorRes = errorRes) }
            return
        }

        val referenceCode = UUID.randomUUID()
            .toString()
            .replace("-", "")
            .take(8)
            .uppercase()
        _uiState.update { it.copy(navigateToSuccessWithCode = referenceCode) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigateToSuccessWithCode = null) }
    }
}
