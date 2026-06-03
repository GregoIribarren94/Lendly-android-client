// ProductCard.kt
package com.lendly.fintech.ui.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import com.lendly.fintech.ui.theme.*

@Composable
fun ProductCard(
    imageRes: Int,
    name: String,
    price: String,
    months: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(132.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundCard
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Imagen
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .width(85.dp)
                    .height(65.dp),
                contentScale = ContentScale.Crop
            )

            // Nombre
            Text(
                text = name,
                style = CaptionMedium,
                color = ContentSecondary,
                maxLines = 1
            )

            // Precio x cuotas
            Text(
                text = "₱$price × $months mo",
                style = LabelSmall,
                color = ContentTertiary,
                maxLines = 1
            )
        }
    }
}
