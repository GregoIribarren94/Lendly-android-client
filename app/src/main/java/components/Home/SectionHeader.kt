// SectionHeader.kt
package com.lendly.fintech.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lendly.fintech.ui.theme.*

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = TitleLarge,
            color = ContentOnSurface
        )
        TextButton(onClick = onSeeAllClick) {
            Text(
                text = "See All →",
                style = ButtonLabel,
                color = ContentOnSurface
            )
        }
    }
}