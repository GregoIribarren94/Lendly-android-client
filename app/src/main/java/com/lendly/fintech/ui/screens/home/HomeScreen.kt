package com.lendly.fintech.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.brand.LendlyLogoMark
import com.lendly.fintech.ui.screens.home.components.AvailableBalanceLabel
import com.lendly.fintech.ui.screens.home.components.CashInButton
import com.lendly.fintech.ui.screens.home.components.ProductCard
import com.lendly.fintech.ui.screens.home.components.SectionHeader
import com.lendly.fintech.ui.screens.home.components.TransactionRow
import com.lendly.fintech.ui.theme.*

// ── Datos de display (placeholder hasta conectar al ViewModel) ────────────────

private data class UnpaidLoan(
    val iconRes: Int,
    val name: String,
    val amount: String,
    val fee: String,
)

private data class RecommendedProduct(
    val imageRes: Int,
    val name: String,
    val price: String,
    val months: String,
)

private val unpaidLoans = listOf(
    UnpaidLoan(R.drawable.ic_nike, "Nike Inc.", "₱400.00", "Fees of February"),
    UnpaidLoan(R.drawable.ic_apple, "Apple Inc.", "₱1500.00", "Fees of March"),
)

private val recommendedProducts = listOf(
    RecommendedProduct(R.drawable.ic_iphone_12_pro, "iPhone 12 Pro", "1,200", "24"),
    RecommendedProduct(R.drawable.ic_notebook, "Work Laptop", "1,200", "24"),
    RecommendedProduct(R.drawable.ic_nike_shoe, "Sneakers", "1,200", "24"),
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
        HomeTopBar()

        // Título de pantalla
        Text(
            text = "Account",
            style = Headline,
            color = ContentPrimary,
            modifier = Modifier.padding(top = Spacing.xs, bottom = Spacing.md),
        )

        // Card de balance
        BalanceCard(
            balance = "₱ 2,500.00",
            onCashIn = onCashIn,
        )

        Spacer(Modifier.height(Spacing.lg))

        // Unpaid Loans
        SectionHeader(title = "Unpaid Loans", onSeeAllClick = onSeeAllLoans)
        Spacer(Modifier.height(Spacing.sm))
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            unpaidLoans.forEach { loan ->
                TransactionRow(
                    iconRes = loan.iconRes,
                    name = loan.name,
                    amount = loan.amount,
                    subtitle = loan.fee,
                )
            }
        }

        Spacer(Modifier.height(Spacing.lg))

        // Recommended For You
        SectionHeader(title = "Recommended For You", onSeeAllClick = onSeeAllRecommended)
        Spacer(Modifier.height(Spacing.sm))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            items(recommendedProducts) { product ->
                ProductCard(
                    imageRes = product.imageRes,
                    name = product.name,
                    price = product.price,
                    months = product.months,
                )
            }
        }
    }
}

// ── Top Bar (persona · logo · campana) ────────────────────────────────────────

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = "Profile",
                tint = ContentPrimary,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(Modifier.weight(1f))
        LendlyLogoMark()
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = "Notifications",
                tint = ContentPrimary,
                modifier = Modifier.size(24.dp),
            )
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