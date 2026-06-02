package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.OnboardingBackground
import com.lendly.fintech.ui.theme.OnboardingSubtitleColor
import com.lendly.fintech.ui.theme.OnboardingTitleColor
import com.lendly.fintech.ui.theme.Spacing

/**
 * Pantalla de confirmacion de identidad verificada (Verified).
 * Se muestra tras completar Face + ID Verification, antes de los datos personales.
 */
@Composable
fun VerifiedScreen(
    onContinue: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = OnboardingBackground,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = Spacing.lg),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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
                    text = "VERIFIED!",
                    style = MaterialTheme.typography.displaySmall,
                    color = OnboardingTitleColor,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = "Your identity has been confirmed.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = OnboardingSubtitleColor,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            AuthBottomBar(
                text = "Continue",
                onClick = onContinue,
                showDivider = false,
                containerColor = OnboardingBackground,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview(name = "Verified", showBackground = true)
@Composable
private fun VerifiedScreenPreview() {
    LendlyTheme {
        VerifiedScreen(onContinue = {})
    }
}
