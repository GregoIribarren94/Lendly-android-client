package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.theme.*

@Composable
fun GridProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(modifier = Modifier.padding(Spacing.sm), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundNeutral),
                contentAlignment = Alignment.Center,
            ) {
                if (!product.image.isNullOrBlank()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                    )
                } else {
                    Text(text = product.emoji(), fontSize = 48.sp)
                }
            }
            Text(text = product.name, style = CaptionMedium, color = ContentSecondary, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = product.brand, style = Caption, color = ContentTertiary, maxLines = 1)
            Text(text = "₱${"%.0f".format(product.price)} × ${product.installments} mo", style = Caption, color = InteractivePrimary, maxLines = 1)
        }
    }
}
