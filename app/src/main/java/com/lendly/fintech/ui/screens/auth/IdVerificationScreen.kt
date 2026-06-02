package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

private val IdVerificationBackground = Color(0xFFFCF8F8)

@Composable
fun IdVerificationScreen(
    onBack: () -> Unit,
    onInfo: () -> Unit,
    onContinue: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = IdVerificationBackground,
        topBar = {
            AuthTopBar(
                onBack = onBack,
                onInfo = onInfo,
            )
        },
        bottomBar = {
            AuthBottomBar(
                text = "Next",
                onClick = onContinue,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(IdVerificationBackground)
                .padding(innerPadding)
                .offset(y = (-8).dp),
        ) {
            Text(
                text = "Let's scan your ID",
                style = Headline,
                color = ContentOnSurface,
                modifier = Modifier.padding(
                    start = Spacing.md,
                    end = Spacing.md,
                ),
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Text(
                text = "Always keep your phone in portrait mode, and here are some more tips.",
                style = Body,
                color = ContentSecondary,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )

            Spacer(modifier = Modifier.height(20.dp))

            IdFramePreview()
        }
    }
}

@Composable
private fun IdFramePreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(357.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.img_id_verification_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val left = 18.dp.toPx()
            val top = 65.dp.toPx()
            val width = size.width - (left * 2f)
            val height = 226.dp.toPx()

            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(InteractiveAccent, Color(0xFF39A0FF)),
                    startY = top,
                    endY = top + height,
                ),
                topLeft = Offset(left, top),
                size = Size(width, height),
                cornerRadius = CornerRadius(14.dp.toPx(), 14.dp.toPx()),
                style = Stroke(width = 4.dp.toPx()),
            )
        }

        Image(
            painter = painterResource(R.drawable.img_id_verification_card),
            contentDescription = "Documento de identidad dentro del marco",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 48.dp, y = 85.dp)
                .width(297.dp)
                .height(187.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}

@Preview(name = "ID Verification", showBackground = true)
@Composable
private fun IdVerificationScreenPreview() {
    LendlyTheme {
        IdVerificationScreen(onBack = {}, onInfo = {}, onContinue = {})
    }
}
