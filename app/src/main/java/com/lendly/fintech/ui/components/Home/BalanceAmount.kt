// BalanceAmount.kt
package com.lendly.fintech.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.ContentAmount

@Composable
fun BalanceAmount(
    amount: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = amount,
        style = Headline,
        color = ContentAmount,
        modifier = modifier
    )
}