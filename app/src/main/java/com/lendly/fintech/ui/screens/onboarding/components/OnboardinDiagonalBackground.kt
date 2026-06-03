package com.lendly.fintech.ui.screens.onboarding.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingDiagonalBackground(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(width = 674.dp, height = 271.dp)
    ) {
        val w = size.width
        val h = size.height

        val path = Path().apply {
            moveTo(0f, h)
            lineTo(w * 0.52f, 0f)
            lineTo(w * 0.92f, 0f)
            lineTo(w * 0.92f, h * 0.86f)
            lineTo(w * 0.86f, h)
            close()
        }

        drawPath(
            path = path,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF8FFF85),
                    Color(0xFF39A0FF)
                ),
                start = Offset(0f, h),
                end = Offset(w * 0.92f, 0f)
            )
        )
    }
}