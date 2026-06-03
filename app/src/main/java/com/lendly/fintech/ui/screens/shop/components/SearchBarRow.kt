package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BaseLight)
            .border(1.dp, BackgroundCircleNeutral, RoundedCornerShape(8.dp))
            .padding(start = Spacing.md, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(onClick = onSearch)
                .padding(end = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = ContentTertiary, modifier = Modifier.size(24.dp))
            Text(text = "Search for product", style = Body, color = ContentTertiary)
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(InteractiveAccent)
                .clickable(onClick = onFilter),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Default.Tune, contentDescription = "Filters", tint = ContentAmount, modifier = Modifier.size(24.dp))
        }
    }
}
