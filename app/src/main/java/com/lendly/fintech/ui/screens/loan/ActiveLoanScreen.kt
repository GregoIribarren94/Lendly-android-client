package com.lendly.fintech.ui.screens.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.navigation.LendlyTopBar
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.DividerNeutral
import com.lendly.fintech.ui.theme.IconTintDark
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

private data class ActiveLoan(
    val merchant: String,
    val product: String,
    val feeLabel: String,
    val amount: String,
)

private data class RecentLoan(
    val date: String,
    val product: String,
    val merchant: String,
    val status: String,
)

// Datos de ejemplo que reproducen el diseño tal cual.
private val SampleActiveLoans = List(3) {
    ActiveLoan(
        merchant = "Apple Inc.",
        product = "iPhone 15 Pro Max",
        feeLabel = "Fees of febuary",
        amount = "1,2555 PHP",
    )
}

private val SampleRecentLoans = List(3) {
    RecentLoan(
        date = "02/08/2024",
        product = "iPhone 15 Pro Max",
        merchant = "Apple Inc.",
        status = "Paid",
    )
}

@Composable
fun ActiveLoanScreen(
    onBack: () -> Unit,
) {
    Scaffold(containerColor = BackgroundScreen) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            ActiveLoanTopBar(onBack = onBack, onCalendar = {})

            Text(
                text = stringResource(R.string.active_loans_title),
                style = MaterialTheme.typography.headlineMedium,
                color = ContentPrimary,
                modifier = Modifier.padding(
                    start = Spacing.md,
                    end = Spacing.md,
                    top = Spacing.xs,
                    bottom = Spacing.md,
                ),
            )

            HorizontalDivider(color = DividerNeutral)

            Column(modifier = Modifier.padding(horizontal = Spacing.md)) {
                LoanSectionHeader(stringResource(R.string.active_loans_section_present))
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    SampleActiveLoans.forEach { ActiveLoanRow(it) }
                }

                Spacer(Modifier.size(Spacing.lg))

                LoanSectionHeader(stringResource(R.string.active_loans_section_recent))
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    SampleRecentLoans.forEach { RecentLoanRow(it) }
                }

                Spacer(Modifier.size(Spacing.lg))
            }
        }
    }
}

@Composable
private fun ActiveLoanTopBar(
    onBack: () -> Unit,
    onCalendar: () -> Unit,
) {
    LendlyTopBar(
        leading = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.active_loans_back),
                    tint = IconTintDark,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        actions = {
            IconButton(onClick = onCalendar) {
                Icon(
                    painter = painterResource(R.drawable.ic_event),
                    contentDescription = stringResource(R.string.active_loans_calendar),
                    tint = IconTintDark,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
    )
}

@Composable
private fun LoanSectionHeader(title: String) {
    Spacer(Modifier.size(Spacing.md))
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        color = ContentTertiary,
    )
    Spacer(Modifier.size(Spacing.sm))
    HorizontalDivider(color = DividerNeutral)
    Spacer(Modifier.size(Spacing.md))
}

@Composable
private fun ActiveLoanRow(loan: ActiveLoan) {
    LoanRow(
        leading = {
            Image(
                painter = painterResource(R.drawable.ic_apple),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.width(32.dp))
        },
        startTop = loan.merchant,
        startBottom = loan.product,
        endTop = loan.feeLabel,
        endBottom = loan.amount,
    )
}

@Composable
private fun RecentLoanRow(loan: RecentLoan) {
    LoanRow(
        leading = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BackgroundCard),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = ContentTertiary,
                    modifier = Modifier.size(24.dp),
                )
            }
            Spacer(Modifier.width(Spacing.md))
        },
        startTop = loan.date,
        startBottom = loan.product,
        endTop = loan.merchant,
        endBottom = loan.status,
    )
}

@Composable
private fun LoanRow(
    leading: @Composable () -> Unit,
    startTop: String,
    startBottom: String,
    endTop: String,
    endBottom: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leading()
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = startTop,
                style = MaterialTheme.typography.bodySmall,
                color = ContentTertiary,
            )
            Text(
                text = startBottom,
                style = MaterialTheme.typography.bodyLarge,
                color = ContentPrimary,
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = endTop,
                style = MaterialTheme.typography.bodySmall,
                color = ContentTertiary,
                textAlign = TextAlign.End,
            )
            Text(
                text = endBottom,
                style = MaterialTheme.typography.bodyLarge,
                color = ContentPrimary,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview(name = "ActiveLoanScreen", showBackground = true, showSystemUi = true)
@Composable
private fun ActiveLoanScreenPreview() {
    LendlyTheme {
        ActiveLoanScreen(onBack = {})
    }
}
