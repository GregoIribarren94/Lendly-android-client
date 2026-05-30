// OnboardingPage.kt
package com.lendly.fintech.ui.screens.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.theme.*

@Composable
fun OnboardingPage(
    imageRes: Int,
    title: String,
    description: String,
    currentStep: Int,
    totalSteps: Int,
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(OnboardingBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(333.dp),
            contentScale = ContentScale.Crop
        )

        // Título + Descripción
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Título
            Text(
                text = title.uppercase(),
                style = OnboardingTitle,
                color = OnboardingTitleColor,
                textAlign = TextAlign.Center
            )

            // Descripción
            Text(
                text = description,
                style = OnboardingSubtitle,
                color = OnboardingSubtitleColor,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // StepIndicator
        StepIndicator(
            totalSteps = totalSteps,
            currentStep = currentStep
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón
        PrimaryButton(
            text = "Get Started",
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
