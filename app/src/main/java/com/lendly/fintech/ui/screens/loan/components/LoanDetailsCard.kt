package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BalanceTitle
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.ButtonLabel
import com.lendly.fintech.ui.theme.CaptionMedium
import com.lendly.fintech.ui.theme.ContentLink
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.LabelSmall
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun LoanDetailsCard(
    onWhatIsThisClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Text(
                text = stringResource(R.string.loan_info_borrow_up_to),
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary,
            )
            Text(
                text = stringResource(R.string.loan_info_amount),
                style = BalanceTitle,
                color = ContentPrimary,
            )
            Text(
                text = stringResource(R.string.loan_info_subject_to_eval),
                style = CaptionMedium,
                color = ContentPrimary,
            )

            Spacer(Modifier.height(Spacing.xs))
            HorizontalDivider(color = BorderNeutral)
            Spacer(Modifier.height(Spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.loan_info_loan_details),
                    style = ButtonLabel,
                    color = ContentPrimary,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = stringResource(R.string.loan_info_what_is_this),
                    style = ButtonLabel.copy(textDecoration = TextDecoration.Underline),
                    color = ContentLink,
                    modifier = Modifier.clickable(onClick = onWhatIsThisClick),
                )
            }

            Spacer(Modifier.height(Spacing.xs))
            HorizontalDivider(color = BorderNeutral)
            Spacer(Modifier.height(Spacing.xs))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                LoanDetailCell(
                    title = stringResource(R.string.loan_info_payable_in_title),
                    value = stringResource(R.string.loan_info_payable_in_value),
                    caption = stringResource(R.string.loan_info_payable_in_caption),
                    modifier = Modifier.weight(1f),
                )
                VerticalDivider(color = BorderNeutral, modifier = Modifier.fillMaxHeight())
                LoanDetailCell(
                    title = stringResource(R.string.loan_info_interest_rate_title),
                    value = stringResource(R.string.loan_info_interest_rate_value),
                    caption = stringResource(R.string.loan_info_interest_rate_caption),
                    modifier = Modifier.weight(1f),
                )
                VerticalDivider(color = BorderNeutral, modifier = Modifier.fillMaxHeight())
                LoanDetailCell(
                    title = stringResource(R.string.loan_info_process_fee_title),
                    value = stringResource(R.string.loan_info_process_fee_value),
                    caption = stringResource(R.string.loan_info_process_fee_caption),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun LoanDetailCell(
    title: String,
    value: String,
    caption: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = Spacing.xs),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = title, style = CaptionMedium, color = ContentPrimary)
        Text(text = value, style = Headline, color = ContentPrimary)
        Text(text = caption, style = LabelSmall, color = ContentPrimary)
    }
}
