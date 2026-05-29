package com.lendly.fintech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lendly.fintech.ui.auth.LoginScreen
import com.lendly.fintech.ui.home.HomeScreen

/**
 * Grafo de navegación de la app. Arranca en Home (placeholder); la redirección a
 * [Routes.LOGIN] ante un 401 la maneja [com.lendly.fintech.ui.LendlyAppRoot].
 */
@Composable
fun LendlyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier,
    ) {
        composable(Routes.HOME) {
            HomeScreen()
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}