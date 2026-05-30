package com.lendly.fintech.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    // TODO: leer estado de sesión real desde un ViewModel
    LaunchedEffect(Unit) {
        delay(1500) // simulación
        onNavigateToOnboarding() // por ahora siempre va a onboarding
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Splash - TODO")
    }
}