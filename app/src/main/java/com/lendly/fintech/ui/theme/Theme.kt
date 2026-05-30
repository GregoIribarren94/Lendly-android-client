package com.lendly.fintech.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.compose.runtime.SideEffect

private val LightColors = lightColorScheme(
    // Primary: el color "principal" de la app (botones, énfasis)
    primary = InteractivePrimary,           // #163300 (verde oscuro)
    onPrimary = BaseContrast,               // texto/íconos sobre primary → blanco
    primaryContainer = InteractiveAccent,   // #7BF179 (verde claro de los botones)
    onPrimaryContainer = ContentPrimary,    // texto sobre el verde claro → verde muy oscuro

    // Secondary: color secundario / acentos suaves
    secondary = InteractiveSecondary,       // #868685 (gris)
    onSecondary = BaseContrast,
    secondaryContainer = BackgroundNeutral, // #E5F5EA (verde muy suave de fondos)
    onSecondaryContainer = ContentPrimary,

    // Tertiary: un tercer color de acento (lo dejo igual al accent por ahora)
    tertiary = InteractiveAccent,
    onTertiary = ContentPrimary,

    // Background: fondo general de las pantallas
    background = BackgroundScreen,          // blanco
    onBackground = ContentPrimary,          // texto sobre fondo → verde oscuro

    // Surface: fondo de cards, sheets, dialogs
    surface = BackgroundElevated,           // blanco
    onSurface = ContentPrimary,
    surfaceVariant = BackgroundNeutral,
    onSurfaceVariant = ContentSecondary,    // texto secundario → gris oscuro

    // Outline: bordes
    outline = BorderNeutral,                // #0E0F0C al 12%
    outlineVariant = BorderOverlay,

    // Error: para mensajes/estados de error
    error = SentimentNegative,              // #A8200D (rojo)
    onError = BaseContrast
)

@Composable
fun LendlyTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColors

    // Hace que la status bar use el color del fondo y los íconos sean oscuros
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}