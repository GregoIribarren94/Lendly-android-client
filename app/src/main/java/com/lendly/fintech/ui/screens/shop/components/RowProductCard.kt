package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.CaptionMedium
import com.lendly.fintech.ui.theme.ContentSecondary

@Composable
fun RowProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(132.dp)
            .height(145.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProductImage(product = product)
            Spacer(Modifier.height(12.dp))
            Text(
                text = product.name,
                style = CaptionMedium,
                color = ContentSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(6.dp))
            ProductInstallmentText(product = product, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ProductImage(product: Product) {
    if (!product.image.isNullOrBlank()) {
        AsyncImage(
            model = product.image,
            contentDescription = product.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(85.dp)
                .height(65.dp),
        )
    } else {
        Box(
            modifier = Modifier
                .width(85.dp)
                .height(65.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = product.emoji(), fontSize = 36.sp)
        }
    }
}
