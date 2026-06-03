package com.lendly.fintech.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.data.local.SessionManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@EntryPoint
@InstallIn(SingletonComponent::class)
private interface SplashEntryPoint {
    fun sessionManager(): SessionManager
}

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    val context = LocalContext.current
    val sessionManager = remember {
        EntryPointAccessors.fromApplication(
            context.applicationContext,
            SplashEntryPoint::class.java,
        ).sessionManager()
    }

    LaunchedEffect(Unit) {
        delay(2000)
        val token = sessionManager.tokenFlow.first()
        if (token.isNullOrBlank()) onNavigateToOnboarding() else onNavigateToMain()
    }

    // Contenedor de la pantalla completa (Centra el bloque del logo en pantalla)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF6EE)),
        contentAlignment = Alignment.Center,
    ) {
        // Lienzo contenedor (Frame 134) con las medidas de Figma.
        // Las capas se alinean al TopStart para mapear las coordenadas "Arriba" e "Izquierda".
        Box(
            modifier = Modifier.size(width = 242.75.dp, height = 83.33.dp),
            contentAlignment = Alignment.TopStart
        ) {
            // Capa 1: Base oscura / Sombra (logo_rectangle_5)
            // Figma -> Izquierda: 28.99px | Arriba: 36.23px
            Image(
                painter = painterResource(R.drawable.logo_rectangle_5),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 213.77.dp, height = 47.1.dp)
                    .offset(x = 28.99.dp, y = 36.23.dp)
            )

            // Capa 2: Rectángulo intermedio translúcido (logo_rectangle_4)
            // Figma -> Izquierda: 14.49px | Arriba: 18.12px
            Image(
                painter = painterResource(R.drawable.logo_rectangle_4),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 213.77.dp, height = 47.1.dp)
                    .offset(x = 14.49.dp, y = 18.12.dp)
            )

            // Capa 3: Tarjeta verde principal (logo_rectangle_3)
            // Figma -> Izquierda: 0px (por defecto) | Arriba: 0px (por defecto)
            Image(
                painter = painterResource(R.drawable.logo_rectangle_3),
                contentDescription = "Lendly Logo",
                modifier = Modifier
                    .size(width = 213.77.dp, height = 47.1.dp)
                    .offset(x = 0.dp, y = 0.dp)
            )
        }
    }
}