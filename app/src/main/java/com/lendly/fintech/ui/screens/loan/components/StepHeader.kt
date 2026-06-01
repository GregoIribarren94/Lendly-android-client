package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.BackgroundNeutral
import com.lendly.fintech.ui.theme.ButtonLabel
import com.lendly.fintech.ui.theme.Caption
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun StepHeader(
    stepLabel: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Text(
            text = stepLabel,
            style = Caption,
            color = ContentTertiary,
            modifier = Modifier
                .background(BackgroundNeutral, MaterialTheme.shapes.small)
                .padding(horizontal = 10.dp, vertical = 4.dp),
        )
        Text(
            text = title,
            style = ButtonLabel,
            color = ContentPrimary,
        )
    }
}
