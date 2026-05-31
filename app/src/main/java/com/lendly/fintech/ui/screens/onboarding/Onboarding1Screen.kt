package com.lendly.fintech.ui.screens.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.screens.onboarding.components.StepIndicator
import com.lendly.fintech.ui.theme.*

@Composable
fun Onboarding1Screen(
    onContinue: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(OnboardingBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Sección superior: imagen + overlays ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)   // altura aumentada para mostrar la figura completa
            ) {

                // 1. Fondo verde con forma diagonal (triángulo inferior-derecho)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path().apply {
                        // Esquina superior-derecha → baja por la derecha
                        // → corta diagonal hacia la izquierda → sube un poco y cierra
                        moveTo(size.width * 0.38f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height * 0.82f)
                        lineTo(size.width * 0.10f, size.height * 0.60f)
                        lineTo(size.width * 0.10f, size.height * 0.38f)
                        close()
                    }
                    drawPath(path = path, color = Color(0xFF3DDC6E))
                }

                // 2. Imagen principal (persona con el teléfono)
                Image(
                    painter = painterResource(id = R.drawable.ic_primera_imagen),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)        // no ocupa todo el ancho
                        .height(420.dp)
                        .align(Alignment.BottomEnd), // alineada a la derecha/abajo
                    contentScale = ContentScale.Fit  // Fit para que no se recorte
                )

                // 3. Figura verde arriba (ic_simbolo) — tarjeta/billete volando
                Image(
                    painter = painterResource(id = R.drawable.ic_simbolo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(117.dp, 40.dp)
                        .absoluteOffset(x = 120.dp, y = 8.dp)
                )

                // 4. Ícono precio (moneda) — izquierda
                Image(
                    painter = painterResource(id = R.drawable.ic_precio),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .absoluteOffset(x = 8.dp, y = 90.dp)
                )

                // 5. Ícono aplauso — centro superior
                Image(
                    painter = painterResource(id = R.drawable.ic_aplauso),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .absoluteOffset(x = 180.dp, y = 55.dp)
                )

                // 6. Cards "Loan Successful" — izquierda centro
                Column(
                    modifier = Modifier
                        .absoluteOffset(x = 16.dp, y = 190.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) {
                        Row(
                            modifier = Modifier
                                .width(157.dp)
                                .background(
                                    color = Color(0x44FFFFFF),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 0.5.dp,
                                    color = Color(0x82FFFFFF),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Loan Successful",
                                fontSize = 8.sp,
                                color = Color.White,
                                fontFamily = Inter
                            )
                            Text(
                                text = "+ ₱ 2000.00",
                                fontSize = 8.sp,
                                color = InteractiveAccent,
                                fontFamily = Inter
                            )
                        }
                    }
                }

                // 7. Emoji carita — justo debajo de las cards
                Image(
                    painter = painterResource(id = R.drawable.ic_carita),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .absoluteOffset(x = 16.dp, y = 240.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Título + Descripción ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "QUICK LOANS",
                    style = OnboardingTitle,
                    color = OnboardingTitleColor,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Trusted for easy,\nfast loan approvals.",
                    style = OnboardingSubtitle,
                    color = OnboardingSubtitleColor,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Step indicator ──
            StepIndicator(
                totalSteps = 3,
                currentStep = 0
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Botón principal ──
            PrimaryButton(
                text = "Get Started",
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}