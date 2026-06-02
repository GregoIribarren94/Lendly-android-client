package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun FaceVerificationScreen(
    onBack: () -> Unit,
    onInfo: () -> Unit,
    onContinue: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundScreen,
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
                .padding(innerPadding)
                .offset(y = (-8).dp),
        ) {
            Text(
                text = "Put your face in the\nframe",
                style = Headline,
                color = ContentOnSurface,
                modifier = Modifier.padding(
                    start = Spacing.md,
                    end = Spacing.md,
                ),
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Text(
                text = "Follow these instruction, and let us get you onboarded.",
                style = Body,
                color = ContentSecondary,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            FaceFramePreview()
        }
    }
}

@Composable
private fun FaceFramePreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(357.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.img_face_frame_reference),
            contentDescription = "Referencia para ubicar el rostro",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val ovalWidth = 146.dp.toPx()
            val ovalHeight = 186.dp.toPx()
            val strokeWidth = 4.dp.toPx()
            val top = 58.dp.toPx()
            val left = (size.width - ovalWidth) / 2f

            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(InteractiveAccent, Color(0xFF39A0FF)),
                    start = Offset(left, top),
                    end = Offset(left + ovalWidth, top + ovalHeight),
                ),
                topLeft = Offset(left, top),
                size = Size(ovalWidth, ovalHeight),
                style = Stroke(width = strokeWidth),
            )
        }
    }
}
