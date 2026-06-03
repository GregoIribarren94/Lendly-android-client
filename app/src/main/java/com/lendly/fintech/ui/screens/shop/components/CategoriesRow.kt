package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.ui.theme.*

private data class CategoryItem(val label: String, val emoji: String)

private val staticCategories = listOf(
    CategoryItem("Phone",      "📱"),
    CategoryItem("Headphones", "🎧"),
    CategoryItem("Laptop",     "💻"),
    CategoryItem("Shoes",      "👟"),
    CategoryItem("Watches",    "⌚"),
)

@Composable
fun CategoriesRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(staticCategories) { cat ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)).background(BackgroundNeutral).clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = cat.emoji, fontSize = 28.sp)
                }
                Text(text = cat.label, style = Caption, color = ContentSecondary)
            }
        }
    }
}
