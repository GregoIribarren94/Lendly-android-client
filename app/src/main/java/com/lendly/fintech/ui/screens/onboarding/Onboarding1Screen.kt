package com.lendly.fintech.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.PrimaryButton
import com.lendly.fintech.ui.components.StepIndicator
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
            // Imagen + elementos flotantes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(333.dp)
            ) {
                // Imagen principal
                Image(
                    painter = painterResource(id = R.drawable.ic_primera_imagen),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Figura verde arriba (ic_simbolo)
                Image(
                    painter = painterResource(id = R.drawable.ic_simbolo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(117.dp, 40.dp)
                        .offset(x = 120.dp, y = 8.dp)
                )

                // Ícono billetera verde
                Image(
                    painter = painterResource(id = R.drawable.ic_precio),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = (-8).dp, y = 62.dp)
                )

                // Ícono aplauso
                Image(
                    painter = painterResource(id = R.drawable.ic_aplauso),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = 99.dp, y = 27.dp)
                )

                // Cards "Loan Successful"
                Column(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 160.dp),
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

                // Emoji carita
                Image(
                    painter = painterResource(id = R.drawable.ic_carita),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = 24.dp, y = 189.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Título + Descripción
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
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

            StepIndicator(
                totalSteps = 3,
                currentStep = 0
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Get Started",
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}