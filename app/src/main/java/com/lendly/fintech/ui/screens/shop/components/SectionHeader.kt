package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lendly.fintech.ui.theme.*

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = BodyEmphasis, color = ContentPrimary)
        TextButton(onClick = onSeeAll) { Text(text = "See All →", style = Caption, color = InteractivePrimary) }
    }
}
