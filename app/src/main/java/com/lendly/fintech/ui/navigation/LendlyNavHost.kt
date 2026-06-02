package com.lendly.fintech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lendly.fintech.ui.screens.auth.LoginScreen
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
        startDestination = Routes.MAIN,
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

        // Bloque ONBOARDING_1 perfectamente cerrado
        composable(Routes.ONBOARDING_1) {
            OnboardingScreen(
                onNavigateToAuth = {
                    navController.navigate(Routes.LOGIN) {
                        // Borra el onboarding del historial para que no vuelva atrás ahí
                        popUpTo(Routes.ONBOARDING_1) { inclusive = true }
                    }
                }
            )
        } // <-- LLAVE CORREGIDA: Aquí cierra correctamente el onboarding

        // Bloque LOGIN independiente y al mismo nivel
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.VERIFY_PHONE) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onForgotPassword = { },
                onChangeProfile = {
                    navController.navigate(Routes.VERIFY_PHONE)
                }
            )
        }

        // Subgrafo de Autenticación
        navigation(
            route = Routes.AUTH_GRAPH,
            startDestination = Routes.VERIFY_PHONE,
        ) {
            composable(Routes.VERIFY_PHONE) {
                VerifyPhoneScreen(
                    onBack = { navController.popBackStack() },
                    onInfo = { },
                    onSendCode = { _, _ ->
                        navController.navigate(Routes.SMS_VERIFICATION)
                    }
                )
            }
            composable(Routes.SMS_VERIFICATION) {
                SmsVerificationScreen(
                    onContinue = { navController.navigate(Routes.FACE_VERIFICATION) },
                    onBack = { navController.popBackStack() },
                )
            }
            // Orden segun Figma: codigo SMS -> Face Verification -> ID Verification -> Verified -> Datos personales.
            composable(Routes.FACE_VERIFICATION) {
                FaceVerificationScreen(
                    onBack = { navController.popBackStack() },
                    onInfo = { },
                    onContinue = { navController.navigate(Routes.ID_VERIFICATION) },
                )
            }
            composable(Routes.ID_VERIFICATION) {
                IdVerificationScreen(
                    onBack = { navController.popBackStack() },
                    onInfo = { },
                    onContinue = { navController.navigate(Routes.VERIFIED) },
                )
            }
            composable(Routes.VERIFIED) {
                VerifiedScreen(
                    onBack = { navController.popBackStack() },
                    onInfo = { },
                    onContinue = { navController.navigate(Routes.PROFILE_FORM) },
                )
            }
            composable(Routes.PROFILE_FORM) { backStackEntry ->
                val authGraphEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.AUTH_GRAPH)
                }
                val registrationViewModel: RegistrationViewModel = hiltViewModel(authGraphEntry)
                ProfileFormScreen(
                    onContinue = { navController.navigate(Routes.SIGNATURE) },
                    onBack = { navController.popBackStack() },
                    viewModel = registrationViewModel,
                )
            }
            composable(Routes.SIGNATURE) {
                SignatureScreen(
                    onBack = { navController.popBackStack() },
                    onInfo = { },
                    onContinue = { navController.navigate(Routes.CREATE_PASSWORD) },
                )
            }
            composable(Routes.CREATE_PASSWORD) { backStackEntry ->
                val authGraphEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.AUTH_GRAPH)
                }
                val registerViewModel: RegisterViewModel = hiltViewModel(authGraphEntry)
                CreatePasswordScreen(
                    onContinue = { navController.navigate(Routes.DONE) },
                    onBack = { navController.popBackStack() },
                    viewModel = registerViewModel,
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
