package com.lendly.fintech.ui.screens.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.OnboardingBackground

@Composable
fun OnboardingBackgroundContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(OnboardingBackground)
            .systemBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.TopStart, unbounded = true)
        ) {
// Aumentando el 'y' bajás el bloque entero sin deformar la diagonal.
            OnboardingDiagonalBackground(
                modifier = Modifier
                    .offset(x = 10.dp, y = 150.dp)
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

