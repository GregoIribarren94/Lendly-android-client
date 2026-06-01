package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.BackgroundNeutral
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.ContentLink
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Caption
import com.lendly.fintech.ui.theme.SectionTitle
import com.lendly.fintech.ui.theme.Spacing
import com.lendly.fintech.ui.theme.TitleMedium

@Composable
fun LoanSummaryCard(
    loanAmount: Double,
    processingFee: Double,
    totalToReceive: Double,
    formatAmount: (Double) -> String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundNeutral)
            .padding(horizontal = Spacing.md, vertical = Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Text(
            text = stringResource(R.string.loan_form_summary_title),
            style = SectionTitle,
            color = ContentPrimary,
        )
        SummaryRow(
            label = stringResource(R.string.loan_form_summary_loan_amount),
            value = stringResource(R.string.loan_form_amount_php_format, formatAmount(loanAmount)),
        )
        SummaryRow(
            label = stringResource(R.string.loan_form_summary_processing_fee),
            value = stringResource(R.string.loan_form_amount_php_negative, formatAmount(processingFee)),
        )
        HorizontalDivider(color = BorderNeutral, thickness = 1.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.loan_form_summary_total),
                style = Caption,
                color = ContentSecondary,
            )
            Text(
                text = stringResource(R.string.loan_form_amount_php_signed, formatAmount(totalToReceive)),
                style = TitleMedium,
                color = ContentPrimary,
            )
        }
        SummaryRow(
            label = stringResource(R.string.loan_form_summary_lender),
            value = stringResource(R.string.loan_form_summary_lender_value),
        )
        Text(
            text = stringResource(R.string.loan_form_summary_what_is_this),
            style = Caption.copy(textDecoration = TextDecoration.Underline),
            color = ContentLink,
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = Caption,
            color = ContentSecondary,
        )
        Text(
            text = value,
            style = Caption,
            color = ContentSecondary,
        )
    }
}
