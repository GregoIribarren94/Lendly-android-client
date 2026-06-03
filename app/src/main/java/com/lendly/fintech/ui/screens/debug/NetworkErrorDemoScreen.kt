package com.lendly.fintech.ui.screens.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.ui.theme.Spacing

/**
 * Pantalla principal (placeholder). Incluye una sección de demostración para
 * verificar el manejo global de errores de red (criterio "Manejo de errores").
 */
@Composable
fun NetworkErrorDemoScreen(
    viewModel: NetworkErrorDemoViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.lg),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Text(
            text = "Hola Lendly",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = "Probar manejo de errores de red",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = Spacing.md),
        )

        Button(
            onClick = viewModel::probarLlamadaReal,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Probar llamada real a la API")
        }
        OutlinedButton(
            onClick = viewModel::simularSinConexion,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simular sin conexión")
        }
        OutlinedButton(
            onClick = viewModel::simularTimeout,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simular timeout")
        }
        OutlinedButton(
            onClick = viewModel::simularError4xx,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simular error 4xx")
        }
        OutlinedButton(
            onClick = viewModel::simularError5xx,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simular error 5xx")
        }
        OutlinedButton(
            onClick = viewModel::simular401,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simular 401 (ir a Login)")
        }
    }
}
