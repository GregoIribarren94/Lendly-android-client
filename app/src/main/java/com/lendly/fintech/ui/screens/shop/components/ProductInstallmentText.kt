package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.theme.Caption
import com.lendly.fintech.ui.theme.ContentTertiary
import java.util.Locale

@Composable
internal fun ProductInstallmentText(product: Product, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = Color(0xFF3C6839))) {
                append("\u20B1${formatAmount(product.installments.toDouble())}")
            }
            withStyle(SpanStyle(color = ContentTertiary)) {
                append(" \u00D7 ${product.installmentMonths} mo")
            }
        },
        style = Caption,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

private fun formatAmount(amount: Double): String = String.format(Locale.US, "%,.0f", amount)
