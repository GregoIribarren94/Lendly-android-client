package com.lendly.fintech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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

        composable(Routes.ONBOARDING_1) {
            Onboarding1Screen(
                onContinue = { navController.navigate(Routes.ONBOARDING_2) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.ONBOARDING_2) {
            Onboarding2Screen(
                onContinue = { navController.navigate(Routes.ONBOARDING_3) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.ONBOARDING_3) {
            Onboarding3Screen(
                onContinue = { navController.navigate(Routes.LOGIN) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.VERIFY_PHONE) },
                onBack = { navController.popBackStack() },
            )
        }

        navigation(
            route = Routes.AUTH_GRAPH,
            startDestination = Routes.VERIFY_PHONE,
        ) {
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
                val registrationViewModel: RegistrationViewModel = hiltViewModel(
                    navController.getBackStackEntry(Routes.AUTH_GRAPH)
                )
                ProfileFormScreen(
                    onContinue = { navController.navigate(Routes.CREATE_PASSWORD) },
                    onBack = { navController.popBackStack() },
                    viewModel = registrationViewModel,
                )
            }
            composable(Routes.CREATE_PASSWORD) {
                val registrationViewModel: RegistrationViewModel = hiltViewModel(
                    navController.getBackStackEntry(Routes.AUTH_GRAPH)
                )
                CreatePasswordScreen(
                    onContinue = { navController.navigate(Routes.DONE) },
                    onBack = { navController.popBackStack() },
                    viewModel = registrationViewModel,
                )
            }
            composable(Routes.DONE) {
                DoneScreen(
                    onContinue = {
                        navController.navigate(Routes.MAIN) {
                            popUpTo(Routes.AUTH_GRAPH) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                )
            }
        }

        composable(Routes.MAIN) {
            MainScreen()
        }
    }
}
