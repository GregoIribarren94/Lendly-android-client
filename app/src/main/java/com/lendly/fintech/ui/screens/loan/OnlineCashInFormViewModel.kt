package com.lendly.fintech.ui.screens.loan

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lendly.fintech.R
import com.lendly.fintech.data.model.OnlineMethod
import com.lendly.fintech.data.model.PaymentMethodType
import com.lendly.fintech.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnlineCashInFormUiState(
    val method: OnlineMethod,
    val amount: String = "",
    @StringRes val amountErrorRes: Int? = null,
    val selectedPaymentType: PaymentMethodType = PaymentMethodType.TRANSFER,
    val accountNumber: String = "",
    @StringRes val accountNumberErrorRes: Int? = null,
    val accountName: String = "",
    @StringRes val accountNameErrorRes: Int? = null,
    val cardNumber: String = "",
    @StringRes val cardNumberErrorRes: Int? = null,
    val cardExpiry: String = "",
    @StringRes val cardExpiryErrorRes: Int? = null,
    val cardCvv: String = "",
    @StringRes val cardCvvErrorRes: Int? = null,
    val cardholderName: String = "",
    @StringRes val cardholderNameErrorRes: Int? = null,
    val navigateToSuccess: Boolean = false,
) {
    val canConfirm: Boolean
        get() {
            if (amount.isBlank() || amountErrorRes != null) return false
            return when (selectedPaymentType) {
                PaymentMethodType.TRANSFER ->
                    accountNumber.isNotBlank() &&
                        accountName.isNotBlank() &&
                        accountNumberErrorRes == null &&
                        accountNameErrorRes == null

                PaymentMethodType.CREDIT_CARD,
                PaymentMethodType.DEBIT_CARD ->
                    cardNumber.isNotBlank() &&
                        cardExpiry.isNotBlank() &&
                        cardCvv.isNotBlank() &&
                        cardholderName.isNotBlank() &&
                        cardNumberErrorRes == null &&
                        cardExpiryErrorRes == null &&
                        cardCvvErrorRes == null &&
                        cardholderNameErrorRes == null
            }
        }
}

@HiltViewModel
class OnlineCashInFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val method: OnlineMethod = runCatching {
        OnlineMethod.valueOf(savedStateHandle.get<String>(Routes.ARG_METHOD_ID).orEmpty())
    }.getOrDefault(OnlineMethod.BPI)

    private val _uiState = MutableStateFlow(OnlineCashInFormUiState(method = method))
    val uiState: StateFlow<OnlineCashInFormUiState> = _uiState.asStateFlow()

    fun onAmountChange(value: String) {
        _uiState.update { it.copy(amount = value, amountErrorRes = null) }
    }

    fun onPaymentTypeSelected(type: PaymentMethodType) {
        _uiState.update { it.copy(selectedPaymentType = type) }
    }

    fun onAccountNumberChange(value: String) {
        _uiState.update { it.copy(accountNumber = value, accountNumberErrorRes = null) }
    }

    fun onAccountNameChange(value: String) {
        _uiState.update { it.copy(accountName = value, accountNameErrorRes = null) }
    }

    fun onCardNumberChange(value: String) {
        _uiState.update { it.copy(cardNumber = value, cardNumberErrorRes = null) }
    }

    fun onCardExpiryChange(value: String) {
        _uiState.update { it.copy(cardExpiry = value, cardExpiryErrorRes = null) }
    }

    fun onCardCvvChange(value: String) {
        _uiState.update { it.copy(cardCvv = value, cardCvvErrorRes = null) }
    }

    fun onCardholderNameChange(value: String) {
        _uiState.update { it.copy(cardholderName = value, cardholderNameErrorRes = null) }
    }

    fun onConfirm() {
        val state = _uiState.value
        val parsedAmount = state.amount.toDoubleOrNull()
        val amountErrorRes = when {
            state.amount.isBlank() -> R.string.online_cash_in_form_amount_error_empty
            parsedAmount == null || parsedAmount <= 0.0 -> R.string.online_cash_in_form_amount_error_zero
            else -> null
        }

        if (amountErrorRes != null) {
            _uiState.update { it.copy(amountErrorRes = amountErrorRes) }
            return
        }

        when (state.selectedPaymentType) {
            PaymentMethodType.TRANSFER -> {
                val accountNumberError = requiredError(state.accountNumber)
                val accountNameError = requiredError(state.accountName)
                if (accountNumberError != null || accountNameError != null) {
                    _uiState.update {
                        it.copy(
                            accountNumberErrorRes = accountNumberError,
                            accountNameErrorRes = accountNameError,
                        )
                    }
                    return
                }
            }

            PaymentMethodType.CREDIT_CARD,
            PaymentMethodType.DEBIT_CARD -> {
                val cardNumberError = requiredError(state.cardNumber)
                val cardExpiryError = requiredError(state.cardExpiry)
                val cardCvvError = requiredError(state.cardCvv)
                val cardholderNameError = requiredError(state.cardholderName)
                if (
                    cardNumberError != null ||
                    cardExpiryError != null ||
                    cardCvvError != null ||
                    cardholderNameError != null
                ) {
                    _uiState.update {
                        it.copy(
                            cardNumberErrorRes = cardNumberError,
                            cardExpiryErrorRes = cardExpiryError,
                            cardCvvErrorRes = cardCvvError,
                            cardholderNameErrorRes = cardholderNameError,
                        )
                    }
                    return
                }
            }
        }

        _uiState.update { it.copy(navigateToSuccess = true) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigateToSuccess = false) }
    }

    @StringRes
    private fun requiredError(value: String): Int? =
        if (value.isBlank()) R.string.online_cash_in_form_field_required else null
}
