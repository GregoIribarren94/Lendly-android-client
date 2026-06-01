package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.BaseLight
import com.lendly.fintech.ui.theme.CaptionMedium
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun HeroCard(modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = InteractiveAccent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                LimitedTimeOfferBadge()
                Text(
                    text = stringResource(R.string.loan_info_hero_title),
                    style = Headline,
                    color = ContentPrimary,
                )
                Text(
                    text = stringResource(R.string.loan_info_hero_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = ContentPrimary,
                )
            }
            Image(
                painter = painterResource(R.drawable.img_undraw_confirmation_re_b6q5_1),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(112.dp),
            )
        }
    }
}

@Composable
private fun LimitedTimeOfferBadge() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(BaseLight)
            .padding(horizontal = Spacing.sm, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_alarm),
            contentDescription = null,
            tint = ContentPrimary,
            modifier = Modifier.size(14.dp),
        )
        Text(
            text = stringResource(R.string.loan_info_limited_time_offer),
            style = CaptionMedium,
            color = ContentPrimary,
        )
    }
}
