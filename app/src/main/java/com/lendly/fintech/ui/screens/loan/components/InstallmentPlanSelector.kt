package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.screens.loan.InstallmentPlan
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.Caption
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.Spacing
import com.lendly.fintech.ui.theme.TitleMedium

@Composable
fun InstallmentPlanSelector(
    plans: List<InstallmentPlan>,
    selectedPlan: InstallmentPlan,
    monthlyPaymentFor: (InstallmentPlan) -> Double,
    formatAmount: (Double) -> String,
    onPlanSelected: (InstallmentPlan) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        plans.forEach { plan ->
            InstallmentPlanCard(
                plan = plan,
                isSelected = plan.months == selectedPlan.months,
                monthlyPayment = monthlyPaymentFor(plan),
                formatAmount = formatAmount,
                onClick = { onPlanSelected(plan) },
            )
        }
    }
}

@Composable
private fun InstallmentPlanCard(
    plan: InstallmentPlan,
    isSelected: Boolean,
    monthlyPayment: Double,
    formatAmount: (Double) -> String,
    onClick: () -> Unit,
) {
    val borderColor = if (isSelected) InteractiveAccent else BorderNeutral
    val borderWidth = if (isSelected) 1.5.dp else 1.dp
    val interestText = "%.2f%%".format(plan.monthlyInterestRate * 100)

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(borderWidth, borderColor),
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md, vertical = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = stringResource(R.string.loan_form_plan_months, plan.months),
                    style = TitleMedium,
                    color = ContentPrimary,
                )
                Text(
                    text = stringResource(R.string.loan_form_plan_interest, interestText),
                    style = Caption,
                    color = ContentTertiary,
                )
            }
            Text(
                text = stringResource(R.string.loan_form_plan_monthly, formatAmount(monthlyPayment)),
                style = TitleMedium,
                color = ContentPrimary,
            )
        }
    }
}
