package com.lendly.fintech.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.LendlyTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = CornerFull,
        colors = ButtonDefaults.buttonColors(
            // Colores cuando está HABILITADO
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.Black,

            // Colores cuando está DESHABILITADO (Forzamos el verde y el texto negro)
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f), // Le da un toque sutil de apagado al verde si querés, o dejalo en 1f para que no cambie nada
            disabledContentColor = Color.Black // <-- ESTO hace que el texto se quede negro fijo siempre
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.Black, // El spinner también se acopla al negro
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black // Aseguramos doblemente que el Text use Negro
            )
        }
    }
}
