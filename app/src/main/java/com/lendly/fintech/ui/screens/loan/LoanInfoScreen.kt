package com.lendly.fintech.ui.screens.loan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.screens.loan.components.HeroCard
import com.lendly.fintech.ui.screens.loan.components.HowItWorksCard
import com.lendly.fintech.ui.screens.loan.components.LoanDetailsCard
import com.lendly.fintech.ui.screens.loan.components.LoanInfoTopBar
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun LoanInfoScreen(
    onApply: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.md, vertical = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            LoanInfoTopBar()
            HeroCard()
            LoanDetailsCard()
            Text(
                text = stringResource(R.string.loan_info_how_it_works),
                style = MaterialTheme.typography.titleLarge,
                color = ContentPrimary,
            )
            HowItWorksGrid()
            Spacer(Modifier.height(Spacing.xs))
            PrimaryButton(
                text = stringResource(R.string.loan_info_cta),
                onClick = onApply,
            )
            Spacer(Modifier.height(Spacing.xs))
        }
    }
}

@Composable
private fun HowItWorksGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            HowItWorksCard(
                imageRes = R.drawable.img_undraw_join_re_w1lh_1,
                title = stringResource(R.string.loan_info_hiw_credit_title),
                body = stringResource(R.string.loan_info_hiw_credit_body),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
            HowItWorksCard(
                imageRes = R.drawable.img_undraw_confirmation_re_b6q5_1,
                title = stringResource(R.string.loan_info_hiw_approval_title),
                body = stringResource(R.string.loan_info_hiw_approval_body),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            HowItWorksCard(
                imageRes = R.drawable.img_frame_41,
                title = stringResource(R.string.loan_info_hiw_payments_title),
                body = stringResource(R.string.loan_info_hiw_payments_body),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
            HowItWorksCard(
                imageRes = R.drawable.img_undraw_security_on_re_e491_1,
                title = stringResource(R.string.loan_info_hiw_secure_title),
                body = stringResource(R.string.loan_info_hiw_secure_body),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
    }
}

@Preview(name = "LoanInfoScreen", showBackground = true, heightDp = 1100)
@Composable
private fun LoanInfoScreenPreview() {
    LendlyTheme {
        LoanInfoScreen(onApply = {}, onBack = {})
    }
}
