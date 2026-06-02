package com.lendly.fintech.ui.screens.auth

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.OnboardingBackground
import com.lendly.fintech.ui.theme.OnboardingSubtitleColor
import com.lendly.fintech.ui.theme.OnboardingTitleColor
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun DoneScreen(
    onContinue: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = OnboardingBackground,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = onContinue,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(Spacing.md)
                    .align(Alignment.TopStart)
                    .size(40.dp)
                    .background(color = Color(0xFF0B390F), shape = CircleShape),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "Cerrar",
                    tint = Color.Unspecified,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = Spacing.lg)
                    .padding(top = Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StackedLogoMark(modifier = Modifier.padding(top = Spacing.xs))

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.check_mark),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Fit,
                )

                Spacer(modifier = Modifier.height(Spacing.xl))

                Text(
                    text = "ALL DONE!",
                    style = MaterialTheme.typography.displaySmall,
                    color = OnboardingTitleColor,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = "You’re ready to start a loan.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = OnboardingSubtitleColor,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            AuthBottomBar(
                text = "Done",
                onClick = onContinue,
                showDivider = false,
                containerColor = OnboardingBackground,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun StackedLogoMark(
    modifier: Modifier = Modifier,
    width: Dp = 80.dp,
) {
    val layerHeight = width * (48f / 187f)
    Box(
        modifier = modifier.size(width = width, height = layerHeight + 14.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_rectangle_5),
            contentDescription = null,
            modifier = Modifier
                .width(width)
                .height(layerHeight)
                .offset(y = 14.dp),
            contentScale = ContentScale.Fit,
        )
        Image(
            painter = painterResource(id = R.drawable.logo_rectangle_4),
            contentDescription = null,
            modifier = Modifier
                .width(width)
                .height(layerHeight)
                .offset(y = 7.dp),
            contentScale = ContentScale.Fit,
        )
        Image(
            painter = painterResource(id = R.drawable.logo_rectangle_3),
            contentDescription = "Lendly",
            modifier = Modifier
                .width(width)
                .height(layerHeight),
            contentScale = ContentScale.Fit,
        )
    }
}

@Preview(name = "Done", showBackground = true)
@Composable
private fun DoneScreenPreview() {
    LendlyTheme {
        DoneScreen(onContinue = {})
    }
}
