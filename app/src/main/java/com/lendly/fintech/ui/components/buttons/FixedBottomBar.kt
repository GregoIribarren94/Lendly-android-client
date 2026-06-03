package com.lendly.fintech.ui.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.Spacing

/**
 * Barra inferior fija y compartida para el CTA principal.
 *
 * Se ancla al fondo de la pantalla (va en el slot `bottomBar` de un `Scaffold`),
 * respeta la barra de navegación del sistema y el teclado (IME), y mantiene el
 * botón siempre en la misma posición. Usarla en todas las pantallas del flujo de
 * préstamo evita que el botón "salte" al navegar entre ellas.
 */
@Composable
fun FixedBottomBar(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Surface(
        color = BackgroundScreen,
        shadowElevation = 8.dp,
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
                .padding(
                    start = Spacing.md,
                    top = Spacing.sm,
                    end = Spacing.md,
                    bottom = Spacing.sm,
                ),
        ) {
            Button(
                onClick = onClick,
                enabled = enabled,
                shape = CornerFull,
                colors = ButtonDefaults.buttonColors(
                    // Siempre el mismo verde, incluso deshabilitado: solo se bloquea el click
                    // hasta completar los datos, sin verse "apagado".
                    containerColor = InteractiveAccent,
                    contentColor = Color.Black,
                    disabledContainerColor = InteractiveAccent,
                    disabledContentColor = Color.Black,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                )
            }
        }
    }
}
