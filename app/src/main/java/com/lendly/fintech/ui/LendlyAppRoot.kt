package com.lendly.fintech.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.lendly.fintech.ui.navigation.LendlyNavHost
import com.lendly.fintech.ui.navigation.Routes

/**
 * Raíz de la UI. Aloja:
 * - El único [SnackbarHost] global: muestra los mensajes publicados en
 *   [com.lendly.fintech.core.SnackbarController] (p. ej. "Sin conexión").
 * - El observador de sesión: ante un 401 ([com.lendly.fintech.core.SessionEventBus])
 *   redirige a [Routes.LOGIN].
 */
@Composable
fun LendlyAppRoot(viewModel: AppViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    // Snackbar global: cualquier mensaje publicado se muestra acá.
    LaunchedEffect(Unit) {
        viewModel.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // 401 global: la sesión expiró → redirigir a Login limpiando el back stack.
    LaunchedEffect(Unit) {
        viewModel.sessionExpired.collect {
            navController.navigate(Routes.LOGIN) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // Solo consumimos el inset superior (status bar). El inferior queda libre para que
        // el BottomNavBar interno (MainScreen) llegue hasta el borde inferior de la pantalla.
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.navigationBarsPadding(),
            )
        },
    ) { innerPadding ->
        LendlyNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}