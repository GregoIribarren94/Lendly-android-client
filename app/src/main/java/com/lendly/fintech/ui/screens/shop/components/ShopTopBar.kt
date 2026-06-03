package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.ui.theme.*

@Composable
fun ShopTopBar(onSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "🌿", fontSize = 26.sp)
        Spacer(Modifier.weight(1f))
        IconButton(onClick = onSearch) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = ContentPrimary, modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications", tint = ContentPrimary, modifier = Modifier.size(24.dp))
        }
    }
}
