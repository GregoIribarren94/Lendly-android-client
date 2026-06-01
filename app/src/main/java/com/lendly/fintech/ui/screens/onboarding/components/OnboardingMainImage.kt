package com.lendly.fintech.ui.screens.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingMainImage(
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        modifier = modifier
            .size(width = 359.dp, height = 333.dp)
            .offset(x = 24.dp, y = 0.dp),
        contentScale = ContentScale.Fit
    )
}