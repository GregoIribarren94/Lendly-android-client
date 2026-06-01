package com.lendly.fintech.ui.screens.onboarding.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.theme.*
import com.lendly.fintech.R

@Composable
fun OnboardingPage(
    title: String,
    description: String,
    currentStep: Int,
    totalSteps: Int,
    buttonText: String,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    graphicContent: @Composable BoxScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(OnboardingBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. Cabecera Fija: El Logo de Lendly 3D
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 24.dp)
                .size(width = 121.37.dp, height = 41.66.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                painter = painterResource(R.drawable.logo_rectangle_5),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 106.88.dp, height = 23.55.dp)
                    .offset(x = 14.49.dp, y = 18.11.dp)
            )
            Image(
                painter = painterResource(R.drawable.logo_rectangle_4),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 106.88.dp, height = 23.55.dp)
                    .offset(x = 7.24.dp, y = 9.06.dp)
            )
            Image(
                painter = painterResource(R.drawable.logo_rectangle_3),
                contentDescription = "Lendly Logo",
                modifier = Modifier
                    .size(width = 106.88.dp, height = 23.55.dp)
                    .offset(x = 0.dp, y = 0.dp)
            )
        }

        // 2. Área gráfica central
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp),
            contentAlignment = Alignment.TopStart
        ) {

            // CAMBIO APLICADO: El Box contenedor del fondo se mantiene unbounded,
            // pero le aumentamos el offset vertical para posicionarlo más abajo de forma limpia.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.TopStart, unbounded = true)
            ) {
                OnboardingDiagonalBackground(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 110.dp) // ← Se actualizó de 62.dp a 150.dp
                )
            }

            // Contenido gráfico original intacto (la chica, stickers y tarjetas)
            // Sigue flotando encima sin enterarse de que el fondo se movió.
            graphicContent()
        }

        // 3. Bloque inferior de información y acciones
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = title.uppercase(),
                    style = OnboardingTitle,
                    color = OnboardingTitleColor,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = description,
                    style = OnboardingSubtitle,
                    color = OnboardingSubtitleColor,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                StepIndicator(
                    totalSteps = totalSteps,
                    currentStep = currentStep
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (currentStep == 2) {
                    OutlinedButton(
                        onClick = onContinue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Text(
                            text = "Log In",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                PrimaryButton(
                    text = buttonText,
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}