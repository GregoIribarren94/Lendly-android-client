package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

/**
 * Pantalla de verificacion de identidad (ID Verification).
 * Placeholder de UI; la captura/validacion del documento se programa mas adelante.
 */
@Composable
fun IdVerificationScreen(
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
                .padding(innerPadding),
        ) {
            Text(
                text = "Verify your\nidentity",
                style = Headline,
                color = ContentOnSurface,
                modifier = Modifier.padding(
                    start = Spacing.md,
                    end = Spacing.md,
                    top = Spacing.md,
                ),
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Text(
                text = "Take a photo of your ID document so we can verify it's really you.",
                style = Body,
                color = ContentSecondary,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Placeholder del area de captura del documento.
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md)
                    .background(
                        color = ContentSecondary.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(Spacing.lg),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "ID document",
                    style = Body,
                    color = ContentSecondary,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.lg))
        }
    }
}

@Preview(name = "ID Verification", showBackground = true)
@Composable
private fun IdVerificationScreenPreview() {
    LendlyTheme {
        IdVerificationScreen(onBack = {}, onInfo = {}, onContinue = {})
    }
}
