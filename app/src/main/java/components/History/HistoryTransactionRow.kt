// HistoryTransactionRow.kt
package com.lendly.fintech.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.*

@Composable
fun HistoryTransactionRow(
    icon: ImageVector,
    time: String,
    title: String,
    company: String,
    amount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Ícono
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = BackgroundCard,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ContentOnSurface
            )
        }

        // Contenido
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Fila superior: hora + empresa
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = time,
                    style = CaptionMedium,
                    color = ContentTertiary
                )
                Text(
                    text = company,
                    style = CaptionMedium,
                    color = ContentTertiary,
                    textAlign = TextAlign.End
                )
            }

            // Fila inferior: título + monto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = Body,
                    color = ContentPrimary
                )
                Text(
                    text = amount,
                    style = Body,
                    color = ContentAmount,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}