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

                Image(
                    painter = painterResource(R.drawable.ic_onboarding1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 359.dp, height = 333.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 24.dp, y = 0.dp),
                    contentScale = ContentScale.Fit
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
        1 -> Box(modifier = Modifier.fillMaxSize())
        2 -> Box(modifier = Modifier.fillMaxSize())
    }
}