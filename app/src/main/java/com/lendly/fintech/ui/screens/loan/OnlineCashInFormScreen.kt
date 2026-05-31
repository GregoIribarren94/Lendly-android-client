package com.lendly.fintech.ui.screens.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.data.model.PaymentMethodType
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.ui.components.navigation.AppTopBar
import com.lendly.fintech.ui.theme.Spacing

private fun formatAmount(raw: String): String =
    raw.toDoubleOrNull()?.let { "\$%,.2f".format(it) } ?: raw

@Composable
fun OnlineCashInFormScreen(
    onSuccess: (amount: String, method: String) -> Unit,
    onBack: () -> Unit,
    viewModel: OnlineCashInFormViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val methodName = stringResource(state.method.nameRes)

    LaunchedEffect(state.navigateToSuccess) {
        if (state.navigateToSuccess) {
            viewModel.onNavigationHandled()
            onSuccess(formatAmount(state.amount), methodName)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.online_cash_in_form_top_bar_title),
                onBackClick = onBack,
            )
        },
        bottomBar = {
            PrimaryButton(
                text = stringResource(R.string.online_cash_in_form_confirm_button),
                onClick = viewModel::onConfirm,
                enabled = state.canConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Spacing.lg, vertical = Spacing.md)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SelectedOnlineMethodCard(state)

            AppTextField(
                value = state.amount,
                onValueChange = viewModel::onAmountChange,
                label = stringResource(R.string.online_cash_in_form_amount_label),
                leadingIcon = {
                    Text(
                        text = "$",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                keyboardType = KeyboardType.Decimal,
                isError = state.amountErrorRes != null,
                errorMessage = state.amountErrorRes?.let { stringResource(it) },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.online_cash_in_form_payment_type_label),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                PaymentMethodType.entries.forEach { type ->
                    FilterChip(
                        selected = state.selectedPaymentType == type,
                        onClick = { viewModel.onPaymentTypeSelected(type) },
                        label = { Text(text = stringResource(type.labelRes)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            when (state.selectedPaymentType) {
                PaymentMethodType.TRANSFER -> {
                    AppTextField(
                        value = state.accountNumber,
                        onValueChange = viewModel::onAccountNumberChange,
                        label = stringResource(R.string.online_cash_in_form_account_number_label),
                        keyboardType = KeyboardType.Number,
                        isError = state.accountNumberErrorRes != null,
                        errorMessage = state.accountNumberErrorRes?.let { stringResource(it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    AppTextField(
                        value = state.accountName,
                        onValueChange = viewModel::onAccountNameChange,
                        label = stringResource(R.string.online_cash_in_form_account_name_label),
                        isError = state.accountNameErrorRes != null,
                        errorMessage = state.accountNameErrorRes?.let { stringResource(it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                PaymentMethodType.CREDIT_CARD,
                PaymentMethodType.DEBIT_CARD -> {
                    AppTextField(
                        value = state.cardNumber,
                        onValueChange = viewModel::onCardNumberChange,
                        label = stringResource(R.string.online_cash_in_form_card_number_label),
                        keyboardType = KeyboardType.Number,
                        isError = state.cardNumberErrorRes != null,
                        errorMessage = state.cardNumberErrorRes?.let { stringResource(it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    ) {
                        AppTextField(
                            value = state.cardExpiry,
                            onValueChange = viewModel::onCardExpiryChange,
                            label = stringResource(R.string.online_cash_in_form_card_expiry_label),
                            keyboardType = KeyboardType.Number,
                            isError = state.cardExpiryErrorRes != null,
                            errorMessage = state.cardExpiryErrorRes?.let { stringResource(it) },
                            modifier = Modifier.weight(1f),
                        )
                        AppTextField(
                            value = state.cardCvv,
                            onValueChange = viewModel::onCardCvvChange,
                            label = stringResource(R.string.online_cash_in_form_card_cvv_label),
                            keyboardType = KeyboardType.Number,
                            isError = state.cardCvvErrorRes != null,
                            errorMessage = state.cardCvvErrorRes?.let { stringResource(it) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    AppTextField(
                        value = state.cardholderName,
                        onValueChange = viewModel::onCardholderNameChange,
                        label = stringResource(R.string.online_cash_in_form_cardholder_name_label),
                        isError = state.cardholderNameErrorRes != null,
                        errorMessage = state.cardholderNameErrorRes?.let { stringResource(it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectedOnlineMethodCard(state: OnlineCashInFormUiState) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(state.method.logoRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp),
                )
            }
            Text(
                text = stringResource(state.method.nameRes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
