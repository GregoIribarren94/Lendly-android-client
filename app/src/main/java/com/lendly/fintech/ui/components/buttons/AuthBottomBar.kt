package com.lendly.fintech.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lendly.fintech.ui.theme.CornerFull
import com.lendly.fintech.ui.theme.InteractiveAccent
import com.lendly.fintech.ui.theme.Spacing

/**
 * Barra inferior unificada del flujo de login/registro (VerifyPhone -> CreatePassword).
 *
 * Toma como referencia el boton de [LoginScreen]: siempre misma altura y forma (pill),
 * mismo color verde "encendido" y texto en negro en todas las pantallas; solo cambia el
 * [text]. El parametro [enabled] bloquea el click sin apagar el color (el diseno pide que
 * el boton se vea siempre encendido).
 */
@Composable
fun AuthBottomBar(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    showDivider: Boolean = true,
    containerColor: Color = Color.White,
) {
    // El recuadro abraza el boton (compacto) y se eleva 20dp para que el borde inferior
    // del boton quede a ~32dp del fondo, a la misma altura que el Continue de
    // VerifiedScreen. Queda fijo abajo (sin imePadding) para que no suba con el teclado.
    // En pantallas con fondo de color (Verified/Done) se pasa containerColor = ese fondo
    // para que el recuadro no se vea blanco.
    Column(
        modifier = modifier
            .fillMaxWidth()
            // Maneja su propio inset inferior (la barra de navegación del sistema); antes lo
            // aportaba el Scaffold raíz, que ahora solo consume el inset superior.
            .navigationBarsPadding()
            .padding(bottom = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor),
        ) {
            if (showDivider) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE0E0E0),
                )
            }

            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = CornerFull,
                colors = ButtonDefaults.buttonColors(
                    containerColor = InteractiveAccent,
                    disabledContainerColor = InteractiveAccent,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md)
                    .padding(vertical = Spacing.sm)
                    .height(56.dp),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
