package com.lendly.fintech.ui.screens.loan

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.lendly.fintech.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlin.math.pow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class InstallmentPlan(
    val months: Int,
    val monthlyInterestRate: Double,
)

enum class LoanPurpose(@StringRes val labelRes: Int) {
    EDUCATIONAL(R.string.loan_form_purpose_educational),
    MEDICAL(R.string.loan_form_purpose_medical),
    BUSINESS(R.string.loan_form_purpose_business),
    PERSONAL(R.string.loan_form_purpose_personal),
    OTHER(R.string.loan_form_purpose_other),
}

internal const val PROCESSING_FEE_RATE = 0.03
internal const val MIN_LOAN_AMOUNT = 500.0

internal val DefaultInstallmentPlans = listOf(
    InstallmentPlan(months = 3, monthlyInterestRate = 0.0349),
    InstallmentPlan(months = 6, monthlyInterestRate = 0.0299),
    InstallmentPlan(months = 9, monthlyInterestRate = 0.0259),
    InstallmentPlan(months = 12, monthlyInterestRate = 0.0199),
)

data class LoanFormUiState(
    val amount: String = "",
    val plans: List<InstallmentPlan> = DefaultInstallmentPlans,
    val selectedPlan: InstallmentPlan = DefaultInstallmentPlans.first(),
    val selectedPurpose: LoanPurpose? = null,
    val isPurposeMenuExpanded: Boolean = false,
    val pendingNavigationRef: String? = null,
) {
    val amountValue: Double
        get() = amount.toDoubleOrNull() ?: 0.0

    val processingFee: Double
        get() = amountValue * PROCESSING_FEE_RATE

    val totalToReceive: Double
        get() = (amountValue - processingFee).coerceAtLeast(0.0)

    val canSubmit: Boolean
        get() = amountValue >= MIN_LOAN_AMOUNT && selectedPurpose != null

    fun monthlyPaymentFor(plan: InstallmentPlan): Double {
        if (amountValue <= 0) return 0.0
        val r = plan.monthlyInterestRate
        val n = plan.months
        return if (r == 0.0) {
            amountValue / n
        } else {
            val factor = (1.0 + r).pow(n)
            amountValue * r * factor / (factor - 1.0)
        }
    }
}

@HiltViewModel
class LoanFormViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoanFormUiState())
    val uiState: StateFlow<LoanFormUiState> = _uiState.asStateFlow()

    fun onAmountChange(value: String) {
        if (!value.matches(AMOUNT_REGEX)) return
        _uiState.update { it.copy(amount = value) }
    }

    fun onPlanSelected(plan: InstallmentPlan) {
        _uiState.update { it.copy(selectedPlan = plan) }
    }

    fun onPurposeSelected(purpose: LoanPurpose) {
        _uiState.update {
            it.copy(selectedPurpose = purpose, isPurposeMenuExpanded = false)
        }
    }

    fun onPurposeMenuToggle() {
        _uiState.update { it.copy(isPurposeMenuExpanded = !it.isPurposeMenuExpanded) }
    }

    fun onPurposeMenuDismiss() {
        _uiState.update { it.copy(isPurposeMenuExpanded = false) }
    }

    fun onSubmit() {
        if (!_uiState.value.canSubmit) return
        val refCode = UUID.randomUUID().toString().replace("-", "").take(8).uppercase()
        _uiState.update { it.copy(pendingNavigationRef = refCode) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(pendingNavigationRef = null) }
    }

    private companion object {
        val AMOUNT_REGEX = Regex("^\\d{0,9}(\\.\\d{0,2})?$")
    }
}
