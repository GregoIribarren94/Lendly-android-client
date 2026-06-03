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

private data class BrandItem(
    val name: String,
    @DrawableRes val background: Int,
    @DrawableRes val logo: Int? = null,
)

private val staticBrands = listOf(
    BrandItem("Apple", R.drawable.img_brand_apple, R.drawable.ic_apple),
    BrandItem("Jordan", R.drawable.img_brand_jordan, R.drawable.ic_logo_jordan),
    BrandItem("Adidas", R.drawable.img_brand_adidas),
)

@Composable
fun BrandsRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        items(staticBrands) { brand ->
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BackgroundCard)
                    .clickable { },
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(96.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                ) {
                    Image(
                        painter = painterResource(id = brand.background),
                        contentDescription = brand.name,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(34.dp)
                        .background(BackgroundCard)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = brand.name,
                        style = CaptionMedium,
                        color = ContentSecondary
                    )

                    brand.logo?.let { logo ->
                        Image(
                            painter = painterResource(id = logo),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.height(16.dp),
                        )
                    }
                }
            }
        }
    }
}