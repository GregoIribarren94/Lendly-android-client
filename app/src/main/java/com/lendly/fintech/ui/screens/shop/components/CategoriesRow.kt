package com.lendly.fintech.ui.screens.shop.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.*

private data class CategoryItem(val label: String, @DrawableRes val image: Int)

private val staticCategories = listOf(
    CategoryItem("Phone", R.drawable.ic_iphone),
    CategoryItem("Headphones", R.drawable.ic_headphones),
    CategoryItem("Apparel", R.drawable.ic_shirt),
    CategoryItem("Laptop", R.drawable.ic_laptop_surface),
    CategoryItem("Shoes", R.drawable.img_shop_shoes),
)

@Composable
fun CategoriesRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        items(staticCategories) { cat ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(96.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackgroundCard)
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(cat.image),
                        contentDescription = cat.label,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(width = 60.dp, height = 80.dp),
                    )
                }
                Text(text = cat.label, style = FormLabel, color = ContentSecondary)
            }
        }
    }
}
