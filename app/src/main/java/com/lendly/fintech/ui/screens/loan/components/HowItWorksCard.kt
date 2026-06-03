package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.BaseLight
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun HowItWorksCard(
    imageRes: Int,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = BaseLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.heightIn(min = 252.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.sm),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary,
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodySmall,
                color = ContentPrimary,
            )
        }
    }
}
