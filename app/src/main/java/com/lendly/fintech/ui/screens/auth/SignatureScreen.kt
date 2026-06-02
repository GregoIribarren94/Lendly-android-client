package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.Body
import com.lendly.fintech.ui.theme.Caption
import com.lendly.fintech.ui.theme.ContentOnSurface
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

private val SignatureBackground = Color(0xFFFCF8F8)
private val SignatureCanvasBackground = Color(0xFFFFFFFF)
private val SignatureInk = Color(0xFF202124)

/** Un trazo de la firma: segmento entre dos puntos del gesto. */
private data class SignatureLine(val start: Offset, val end: Offset)

/**
 * Pantalla de firma: el usuario dibuja su firma con el dedo o un stylus.
 * En el flujo va despues de ProfileForm y antes de CreatePassword.
 */
@Composable
fun SignatureScreen(
    onBack: () -> Unit,
    onInfo: () -> Unit,
    onContinue: () -> Unit,
) {
    val lines = remember { mutableStateListOf<SignatureLine>() }
    val hasSignature = lines.isNotEmpty()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SignatureBackground,
        topBar = {
            AuthTopBar(
                onBack = onBack,
                onInfo = onInfo,
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "By tapping \"Next\", you confirm that the " +
                        "information you provided is true and correct.",
                    style = Caption.copy(fontSize = 14.sp),
                    color = ContentSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.lg),
                )

                AuthBottomBar(
                    text = "Next",
                    onClick = onContinue,
                    enabled = hasSignature,
                    showDivider = false,
                    containerColor = SignatureBackground,
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(
                text = "Let's seal the deal!",
                style = Headline,
                color = ContentOnSurface,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Text(
                text = "You can use your finger or a compatible " +
                    "stylus to write you signature",
                style = Body,
                color = ContentSecondary,
                modifier = Modifier.padding(horizontal = Spacing.md),
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            SignatureCanvas(lines = lines)
        }
    }
}

@Composable
private fun SignatureCanvas(lines: MutableList<SignatureLine>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(357.dp)
            .background(SignatureCanvasBackground)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val end = change.position
                    val start = end - dragAmount
                    lines.add(SignatureLine(start, end))
                }
            },
    ) {
        // Marca "x ----" arriba a la derecha (vector exacto del diseno).
        Image(
            painter = painterResource(R.drawable.ic_signature_mark),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = Spacing.md, end = Spacing.md),
        )

        // Trazos de la firma.
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.dp.toPx()
            lines.forEach { line ->
                drawLine(
                    color = SignatureInk,
                    start = line.start,
                    end = line.end,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                )
            }
        }

        // Placeholder, visible solo mientras no hay firma.
        if (lines.isEmpty()) {
            Text(
                text = "Sign here\n(same signature as with the\ndocument you provided)",
                style = Body,
                color = ContentSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview(name = "Signature", showBackground = true)
@Composable
private fun SignatureScreenPreview() {
    LendlyTheme {
        SignatureScreen(onBack = {}, onInfo = {}, onContinue = {})
    }
}
