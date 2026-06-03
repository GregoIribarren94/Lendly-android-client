package com.lendly.fintech.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.navigation.MainTopBar
import com.lendly.fintech.ui.screens.home.components.AvailableBalanceLabel
import com.lendly.fintech.ui.screens.home.components.CashInButton
import com.lendly.fintech.ui.screens.home.components.ProductCard
import com.lendly.fintech.ui.screens.home.components.SectionHeader
import com.lendly.fintech.ui.screens.home.components.TransactionRow
import com.lendly.fintech.ui.theme.*

// ── Datos de display (placeholder hasta conectar al ViewModel) ────────────────

private data class UnpaidLoan(
    val iconRes: Int,
    val nameRes: Int,
    val amountRes: Int,
    val feeRes: Int,
)

private data class RecommendedProduct(
    val imageRes: Int,
    val nameRes: Int,
    val priceRes: Int,
    val monthsRes: Int,
)

private val unpaidLoans = listOf(
    UnpaidLoan(R.drawable.ic_nike, R.string.home_loan_nike_name, R.string.home_loan_nike_amount, R.string.home_loan_nike_fee),
    UnpaidLoan(R.drawable.ic_apple, R.string.home_loan_apple_name, R.string.home_loan_apple_amount, R.string.home_loan_apple_fee),
)

private val recommendedProducts = listOf(
    RecommendedProduct(R.drawable.ic_iphone_12_pro, R.string.home_product_iphone_name, R.string.home_product_price_placeholder, R.string.home_product_months_placeholder),
    RecommendedProduct(R.drawable.ic_headphones, R.string.home_product_headphones_name, R.string.home_product_price_placeholder, R.string.home_product_months_placeholder),
    RecommendedProduct(R.drawable.ic_nike_shoe, R.string.home_product_sneakers_name, R.string.home_product_price_placeholder, R.string.home_product_months_placeholder),
)

// ── HomeScreen ────────────────────────────────────────────────────────────────
@Preview
@Composable
fun HomeScreen(
    onCashIn: () -> Unit = {},
    onSeeAllLoans: () -> Unit = {},
    onSeeAllRecommended: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.md)
            .padding(bottom = Spacing.lg),
    ) {
        MainTopBar()

        // Título de pantalla
        Text(
            text = stringResource(R.string.home_account_title),
            style = Headline,
            color = ContentPrimary,
            modifier = Modifier.padding(top = Spacing.xs, bottom = Spacing.md),
        )

        // Card de balance
        BalanceCard(
            balance = stringResource(R.string.home_balance_amount),
            onCashIn = onCashIn,
        )

        Spacer(Modifier.height(Spacing.lg))

        // Unpaid Loans
        SectionHeader(title = stringResource(R.string.home_unpaid_loans_section), onSeeAllClick = onSeeAllLoans)
        Spacer(Modifier.height(Spacing.sm))
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            unpaidLoans.forEach { loan ->
                TransactionRow(
                    iconRes = loan.iconRes,
                    name = stringResource(loan.nameRes),
                    amount = stringResource(loan.amountRes),
                    subtitle = stringResource(loan.feeRes),
                )
            }
        }

        Spacer(Modifier.height(Spacing.lg))

        // Recommended For You
        SectionHeader(title = stringResource(R.string.home_recommended_section), onSeeAllClick = onSeeAllRecommended)
        Spacer(Modifier.height(Spacing.sm))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            items(recommendedProducts) { product ->
                ProductCard(
                    imageRes = product.imageRes,
                    name = stringResource(product.nameRes),
                    price = stringResource(product.priceRes),
                    months = stringResource(product.monthsRes),
                )
            }
        }
    }
}

// ── Card de balance ───────────────────────────────────────────────────────────

@Composable
private fun BalanceCard(
    balance: String,
    onCashIn: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCard)
            .padding(Spacing.lg),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AvailableBalanceLabel(modifier = Modifier.weight(1f))
            CashInButton(onClick = onCashIn)
        }
        Text(
            text = balance,
            style = BalanceTitle,
            color = ContentAmount,
        )
    }
}
