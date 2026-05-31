package com.lendly.fintech.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AvailableBalanceLabel() {
    Text(
        text = "AVAILABLE BALANCE",
        style = MaterialTheme.typography.bodyLarge,
        color = Color(0xFF454745)
    )
}