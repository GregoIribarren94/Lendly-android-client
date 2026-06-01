package com.lendly.fintech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lendly.fintech.ui.screens.auth.*
import com.lendly.fintech.ui.screens.onboarding.*
import com.lendly.fintech.ui.screens.splash.SplashScreen

/**
 * Root NavHost: maneja el flujo lineal sin BottomBar.
 * Splash -> Onboarding -> Auth -> MainScreen (que internamente arma su propio NavHost).
 */
@Composable
fun LendlyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier,
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Routes.ONBOARDING_1) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
            )
        }

        // Reemplazá los tres bloques composable de ONBOARDING_1, 2 y 3 por este único bloque:

        composable(Routes.ONBOARDING_1) {
            OnboardingScreen(
                onNavigateToAuth = {
                    navController.navigate(Routes.LOGIN) {
                        // Borra el onboarding del historial para que si el usuario tira para atrás desde el Login no vuelva ahí
                        popUpTo(Routes.ONBOARDING_1) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.VERIFY_PHONE) },
                onForgotPassword = { /* TODO: pantalla de recupero de contraseña aún no existe */ },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.VERIFY_PHONE) {
            VerifyPhoneScreen(
                onContinue = { navController.navigate(Routes.SMS_VERIFICATION) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.SMS_VERIFICATION) {
            SmsVerificationScreen(
                onContinue = { navController.navigate(Routes.PROFILE_FORM) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.PROFILE_FORM) {
            ProfileFormScreen(
                onContinue = { navController.navigate(Routes.CREATE_PASSWORD) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.CREATE_PASSWORD) {
            CreatePasswordScreen(
                onContinue = { navController.navigate(Routes.DONE) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.DONE) {
            DoneScreen(
                onContinue = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.MAIN) {
            MainScreen()
        }
    }
}