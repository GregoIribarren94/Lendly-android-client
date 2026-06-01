package com.lendly.fintech.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.screens.onboarding.components.OnboardingPage

@Composable
fun OnboardingScreen(
    onNavigateToAuth: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }

    when (currentStep) {
        0 -> {
            OnboardingPage(
                title = "QUICK LOANS",
                description = "Trusted for easy,\nfast loan approvals.",
                currentStep = 0,
                totalSteps = 3,
                buttonText = "Get Started",
                onContinue = { currentStep = 1 }
            ) {
                // Contenedor gráfico con la altura exacta fijada en tu Figma (481px -> 481.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(481.dp),
                    contentAlignment = Alignment.TopStart
                ) {

                    // 1. Imagen Principal (Chica + Fondo diagonal verde menta)
                    // Se posiciona alineada al fondo a la derecha del contenedor para respetar la perspectiva
                    Image(
                        painter = painterResource(R.drawable.ic_onboarding1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 359.dp, height = 333.dp)
                            .align(Alignment.BottomEnd),
                        contentScale = ContentScale.Fit
                    )

                    // 2. Tarjeta flotante superior (Manitos aplaudiendo 👏)
                    Image(
                        painter = painterResource(R.drawable.ic_aplauso),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = 110.dp, y = 140.dp)
                            .size(30.dp)
                    )

                    // 3. Ícono flotante redondo verde (Billetes/Card 💵)
                    Image(
                        painter = painterResource(R.drawable.ic_precio),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = 40.dp, y = 40.dp)
                            .size(40.dp)
                    )

                    // 4. Bloque de tarjetas "Loan Successful" apiladas
                    Column(
                        modifier = Modifier
                            .offset(x = 24.dp, y = 235.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(2) {
                            Row(
                                modifier = Modifier
                                    .size(width = 157.dp, height = 29.64.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFFF45).copy(alpha = 0.27f))
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Loan Successful",
                                    color = Color(0xFFE5F5EA),
                                    fontSize = 8.32.sp
                                )
                                Text(
                                    "+ ₱ 2000.00",
                                    color = Color(0xFF7BF179),
                                    fontSize = 8.32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // 5. Carita Feliz flotante (😃)
                    Image(
                        painter = painterResource(R.drawable.ic_carita),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = 45.dp, y = 320.dp)
                            .size(24.dp)
                    )


                    // 6. El Logo de Lendly 3D flotando arriba centrado
                    Box(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(top = 24.dp)
                            // Reducimos el tamaño de forma proporcional para la cabecera (Mantiene la relación de aspecto)
                            .size(width = 121.37.dp, height = 41.66.dp)
                            .align(Alignment.TopCenter),
                        contentAlignment = Alignment.TopStart // <- Clave: los offsets se calculan desde la esquina superior izquierda
                    ) {
                        // Capa 1: Base oscura / Sombra (Se dibuja al fondo)
                        // Desfasaje proporcional exacto (la mitad de los píxeles originales de Figma)
                        Image(
                            painter = painterResource(R.drawable.logo_rectangle_5),
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 106.88.dp, height = 23.55.dp)
                                .offset(x = 14.49.dp, y = 18.11.dp)
                        )

                        // Capa 2: Rectángulo intermedio translúcido
                        Image(
                            painter = painterResource(R.drawable.logo_rectangle_4),
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 106.88.dp, height = 23.55.dp)
                                .offset(x = 7.24.dp, y = 9.06.dp)
                        )

                        // Capa 3: Tarjeta verde principal (Queda al frente)
                        Image(
                            painter = painterResource(R.drawable.logo_rectangle_3),
                            contentDescription = "Lendly Logo",
                            modifier = Modifier
                                .size(width = 106.88.dp, height = 23.55.dp)
                                .offset(x = 0.dp, y = 0.dp)
                        )
                    }
                }
            }
        }
        1 -> Box(modifier = Modifier.fillMaxSize())
        2 -> Box(modifier = Modifier.fillMaxSize())
    }
}