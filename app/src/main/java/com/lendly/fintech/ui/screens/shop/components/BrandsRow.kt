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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lendly.fintech.ui.theme.*

private data class BrandItem(val name: String, val logo: String)

// Brands con logos reales de la API
private val staticBrands = listOf(
    BrandItem("Apple",   "https://img.logo.dev/apple.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
    BrandItem("Samsung", "https://img.logo.dev/samsung.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
    BrandItem("Nike",    "https://img.logo.dev/nike.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
    BrandItem("Sony",    "https://img.logo.dev/sony.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
)

@Composable
fun BrandsRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(staticBrands) { brand ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .width(96.dp)
                        .height(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackgroundNeutral)
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = brand.logo,
                        contentDescription = brand.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(48.dp).padding(4.dp),
                    )
                }
                Text(text = brand.name, style = Caption, color = ContentSecondary)
            }
        }
    }
}
