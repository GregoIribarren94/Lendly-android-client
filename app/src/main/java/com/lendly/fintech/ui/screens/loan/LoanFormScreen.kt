package com.lendly.fintech.ui.screens.loan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.screens.loan.components.InstallmentPlanSelector
import com.lendly.fintech.ui.screens.loan.components.LoanAmountField
import com.lendly.fintech.ui.screens.loan.components.LoanFormTopBar
import com.lendly.fintech.ui.screens.loan.components.LoanPurposeDropdown
import com.lendly.fintech.ui.screens.loan.components.LoanSummaryCard
import com.lendly.fintech.ui.screens.loan.components.StepHeader
import com.lendly.fintech.ui.theme.Caption
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LoanHeadingStyle = Headline.copy(
    fontSize = 24.sp,
    lineHeight = 32.sp,
    fontWeight = FontWeight.SemiBold,
)

private fun formatAmount(value: Double): String = "%,.2f".format(value)

@Composable
fun LoanFormScreen(
    onSubmit: (amount: String, method: String, refCode: String) -> Unit,
    onBack: () -> Unit,
    viewModel: LoanFormViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val methodLabel = stringResource(R.string.loan_form_method)
    val formattedTotalForSuccess = stringResource(
        R.string.loan_form_amount_php_signed,
        formatAmount(state.totalToReceive),
    )

    LaunchedEffect(state.pendingNavigationRef) {
        val ref = state.pendingNavigationRef ?: return@LaunchedEffect
        viewModel.onNavigationHandled()
        onSubmit(formattedTotalForSuccess, methodLabel, ref)
    }

    Scaffold(
        topBar = {
            LoanFormTopBar(
                onBack = onBack,
                onInfoClick = { /* TODO: open loan info modal */ },
            )
        },
        bottomBar = {
            PrimaryButton(
                text = stringResource(R.string.loan_form_cta),
                onClick = viewModel::onSubmit,
                enabled = state.canSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = Spacing.md, vertical = Spacing.md),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            LoanFormHeader()
            LoanFormBody(
                state = state,
                onAmountChange = viewModel::onAmountChange,
                onPlanSelected = viewModel::onPlanSelected,
                onPurposeMenuToggle = viewModel::onPurposeMenuToggle,
                onPurposeSelected = viewModel::onPurposeSelected,
                onPurposeMenuDismiss = viewModel::onPurposeMenuDismiss,
            )
            LoanSummaryCard(
                loanAmount = state.amountValue,
                processingFee = state.processingFee,
                totalToReceive = state.totalToReceive,
                formatAmount = ::formatAmount,
            )
        }
    }
}

@Composable
private fun LoanFormHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.xs),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Text(
            text = stringResource(R.string.loan_form_heading),
            style = LoanHeadingStyle,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = stringResource(R.string.loan_form_subheading),
            style = Caption,
            color = ContentSecondary,
        )
    }
}

@Composable
private fun LoanFormBody(
    state: LoanFormUiState,
    onAmountChange: (String) -> Unit,
    onPlanSelected: (InstallmentPlan) -> Unit,
    onPurposeMenuToggle: () -> Unit,
    onPurposeSelected: (LoanPurpose) -> Unit,
    onPurposeMenuDismiss: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            StepHeader(
                stepLabel = stringResource(R.string.loan_form_step_label, 1),
                title = stringResource(R.string.loan_form_step1_title),
            )
            LoanAmountField(
                amount = state.amount,
                onAmountChange = onAmountChange,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            StepHeader(
                stepLabel = stringResource(R.string.loan_form_step_label, 2),
                title = stringResource(R.string.loan_form_step2_title),
            )
            InstallmentPlanSelector(
                plans = state.plans,
                selectedPlan = state.selectedPlan,
                monthlyPaymentFor = state::monthlyPaymentFor,
                formatAmount = ::formatAmount,
                onPlanSelected = onPlanSelected,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            StepHeader(
                stepLabel = stringResource(R.string.loan_form_step_label, 3),
                title = stringResource(R.string.loan_form_step3_title),
            )
            LoanPurposeDropdown(
                purposes = LoanPurpose.entries,
                selected = state.selectedPurpose,
                expanded = state.isPurposeMenuExpanded,
                onExpandedChange = onPurposeMenuToggle,
                onSelect = onPurposeSelected,
                onDismiss = onPurposeMenuDismiss,
            )
        }
    }
}

@Preview(name = "LoanFormScreen", showBackground = true, heightDp = 900)
@Composable
private fun LoanFormScreenPreview() {
    LendlyTheme {
        LoanFormScreen(
            onSubmit = { _, _, _ -> },
            onBack = {},
        )
    }
}
