package com.lendly.fintech.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lendly.fintech.ui.theme.Spacing

/**
 * Pantalla de Login (placeholder). Es el destino al que se redirige cuando la
 * sesión expira (401). Más adelante se reemplazará por el flujo de auth real.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = "Tu sesión expiró. Ingresá nuevamente para continuar.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = Spacing.sm),
        )
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier.padding(top = Spacing.lg),
        ) {
            Text("Ingresar")
        }
    }
}