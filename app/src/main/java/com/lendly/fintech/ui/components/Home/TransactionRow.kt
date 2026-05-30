// TransactionRow.kt
package com.lendly.fintech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.*

@Composable
fun TransactionRow(
    iconRes: Int,
    name: String,
    amount: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BackgroundCard,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Nombre
        Text(
            text = name,
            style = TitleMedium,
            color = ContentOnSurface,
            modifier = Modifier.weight(1f)
        )

        // Monto + Subtexto
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = amount,
                style = ButtonLabel,
                color = ContentOnSurface,
                textAlign = TextAlign.End
            )
            Text(
                text = subtitle,
                style = CaptionMedium,
                color = ContentTertiary,
                textAlign = TextAlign.End
            )
        }
    }
}