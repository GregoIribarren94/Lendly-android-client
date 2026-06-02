
package com.lendly.fintech.ui.screens.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.lendly.fintech.ui.theme.CaptionMedium
import com.lendly.fintech.ui.theme.ContentSecondary

@Composable
fun AvailableBalanceLabel(modifier: Modifier = Modifier) {
    Text(
        text = "AVAILABLE BALANCE",
        style = CaptionMedium,
        color = ContentSecondary,
        textAlign = TextAlign.Left,
        modifier = modifier
    )
}
