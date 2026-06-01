package com.lendly.fintech.ui.screens.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.theme.*

@Composable
fun OnboardingPage(
    title: String,
    description: String,
    currentStep: Int,
    totalSteps: Int,
    buttonText: String,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    graphicContent: @Composable BoxScope.() -> Unit // <- Permite inyectar layouts complejos
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(OnboardingBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Área gráfica superior y central (Usa el tamaño explícito que le pasamos)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            graphicContent()
        }

        // Bloque inferior de textos, indicadores y botones
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                StepIndicator(
                    totalSteps = totalSteps,
                    currentStep = currentStep
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = buttonText,
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}