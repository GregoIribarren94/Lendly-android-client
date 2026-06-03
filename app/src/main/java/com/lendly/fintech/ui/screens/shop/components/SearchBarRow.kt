package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.*

@Composable
fun SearchBarRow(onSearch: () -> Unit, onFilter: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BackgroundNeutral)
                .clickable(onClick = onSearch)
                .padding(horizontal = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = ContentTertiary, modifier = Modifier.size(20.dp))
            Text(text = "Search for product", style = Body, color = ContentTertiary)
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(InteractivePrimary)
                .clickable(onClick = onFilter),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Filters", tint = BaseContrast, modifier = Modifier.size(20.dp))
        }
    }
}
