package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.BodyEmphasis
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

private val VerifiedBackground = Color(0xFFFCF8F8)
private val VerifiedCardBackground = Color(0xFFFFFFFF)
private val VerifiedCardBorder = Color(0xFFE5E2E1)

/**
 * Pantalla de verificacion exitosa: confirma que el rostro y el documento coinciden.
 * Se muestra tras completar Face + ID Verification, antes de los datos personales.
 */
@Composable
fun VerifiedScreen(
    onBack: () -> Unit,
    onInfo: () -> Unit,
    onContinue: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = VerifiedBackground,
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
                .background(VerifiedBackground)
                .padding(innerPadding)
                .padding(horizontal = Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(Spacing.xl))

            Image(
                painter = painterResource(R.drawable.img_verified_shield),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(108.dp)
                    .height(130.dp),
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            Text(
                text = "Woah, Your face and ID\nare the same!",
                style = Headline,
                color = ContentOnSurface,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Text(
                text = "We are just a few questions away from " +
                    "opening your own lendly loan account. Tap " +
                    "the button to continue.",
                style = Body,
                color = ContentSecondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            SecurityGuardCard()
        }
    }
}

@Composable
private fun SecurityGuardCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VerifiedCardBackground)
            .border(
                width = 1.dp,
                color = VerifiedCardBorder,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(horizontal = 12.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Security Guard",
            style = BodyEmphasis,
            color = ContentOnSurface,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        Text(
            text = """Our online security feature world-class
protection against hackers. It makes
them cry and rethink their purpose
in life.""",
            style = Body.copy(lineHeight = 20.sp),
            color = ContentSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Verified", showBackground = true)
@Composable
private fun VerifiedScreenPreview() {
    LendlyTheme {
        VerifiedScreen(onBack = {}, onInfo = {}, onContinue = {})
    }
}
