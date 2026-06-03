package com.lendly.fintech.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.lendly.fintech.ui.screens.onboarding.components.OnboardingMainImage
import com.lendly.fintech.ui.screens.onboarding.components.OnboardingPage
import com.lendly.fintech.ui.screens.onboarding.components.PaymentMiniCard
import com.lendly.fintech.ui.screens.onboarding.components.ProductMiniCard

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

                OnboardingMainImage(
                    imageRes = R.drawable.ic_onboarding1,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
                Image(
                    painter = painterResource(R.drawable.ic_aplauso),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 125.dp, y = 72.dp)
                        .size(30.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_precio),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 18.dp, y = 112.dp)
                        .size(40.dp)
                )

                Column(
                    modifier = Modifier
                        .offset(x = 26.dp, y = 146.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) {
                        Row(
                            modifier = Modifier
                                .size(width = 157.dp, height = 29.64.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.White.copy(alpha = 0.27f))
                                .padding(start = 8.dp, end = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Loan Successful",
                                color = Color(0xFFE5F5EA),
                                fontSize = 7.8.sp,
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "+ ₱ 2000.00",
                                color = Color(0xFF7BF179),
                                fontSize = 7.8.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }

                Image(
                    painter = painterResource(R.drawable.ic_carita),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 55.dp, y = 236.dp)
                        .size(30.dp)
                )

            }
        }
        // Quedan listos para que consumas tu OnboardingPage con los datos del paso 2 y 3
        1 -> {
            OnboardingPage(
                title = "LOAN PRODUCT\nIN-APP",
                description = "Many products to loan.",
                currentStep = 1,
                totalSteps = 3,
                buttonText = "Get Started",
                onContinue = { currentStep = 2 }
            ) {
                OnboardingMainImage(
                    imageRes = R.drawable.ic_onboarding2,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
                Image(
                    painter = painterResource(R.drawable.ic_bolsa_compras),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 20.dp, y = 28.dp)
                        .size(44.dp),
                    alpha = 1f
                )

                Row(
                    modifier = Modifier.offset(x = 42.dp, y = 51.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    ProductMiniCard(
                        imageRes = R.drawable.ic_notebook,
                        text = "iPhone 12 Pro..."
                    )

                    ProductMiniCard(
                        imageRes = R.drawable.ic_iphone,
                        text = "iPhone 12 Pro..."
                    )
                }

                Image(
                    painter = painterResource(R.drawable.ic_sorpresa),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 235.dp, y = 91.dp)
                        .size(35.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_billetes),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 66.dp, y = 147.dp)
                        .size(50.dp)
                )
            }
        }

        2 -> {
            OnboardingPage(
                title = "TRACK & PAY\nEASILY",
                description = "",
                currentStep = 2,
                totalSteps = 3,
                buttonText = "Sign up for free",
                onContinue = onNavigateToAuth
            ) {
                OnboardingMainImage(
                    imageRes = R.drawable.ic_onboarding3,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

                Image(
                    painter = painterResource(R.drawable.ic_cash),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 38.dp, y = 100.dp)
                        .size(45.dp)
                )

                PaymentMiniCard(
                    modifier = Modifier.offset(x = 19.dp, y = 145.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 198.dp, y = 119.dp)
                        .size(49.dp),
                    alpha = 1f
                )

                Image(
                    painter = painterResource(R.drawable.ic_smile),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 96.dp, y = 210.dp)
                        .size(38.dp)
                )
            }
        }
    }
}