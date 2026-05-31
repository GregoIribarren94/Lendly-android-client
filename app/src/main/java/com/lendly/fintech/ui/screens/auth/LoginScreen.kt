package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.ui.components.inputs.PhoneNumberInput
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

/**
 * Pantalla de inicio de sesión: teléfono + contraseña.
 *
 * Delega toda la lógica (validación, llamada a la API, persistencia del token) en
 * [LoginViewModel] y reacciona a [LoginUiState]. Cuando el login es exitoso navega a Home.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onLoginSuccess()
    }

    LoginContent(
        state = uiState,
        onPhoneChange = viewModel::onPhoneChange,
        onPasswordChange = viewModel::onPasswordChange,
        onCountrySelected = viewModel::onCountrySelected,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onSubmit = viewModel::onSubmit,
        onForgotPassword = onForgotPassword,
        onNavigateToRegister = onNavigateToRegister,
        onBack = onBack,
    )
}

@Composable
private fun LoginContent(
    state: LoginUiState,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCountrySelected: (com.lendly.fintech.ui.components.inputs.Country) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmit: () -> Unit,
    onForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onBack: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.lg, vertical = Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.Start),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.md))

            Image(
                painter = painterResource(id = R.drawable.logo_lendly),
                contentDescription = "Lendly",
                modifier = Modifier.height(48.dp),
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            Text(
                text = "Log In",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            PhoneNumberInput(
                phoneNumber = state.phone,
                onPhoneNumberChange = onPhoneChange,
                selectedCountry = state.country,
                onCountrySelected = onCountrySelected,
                label = "Phone number",
                isError = state.phoneError != null,
                errorMessage = state.phoneError,
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            AppTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = "Password",
                keyboardType = KeyboardType.Password,
                visualTransformation = if (state.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = state.passwordError != null,
                errorMessage = state.passwordError,
                trailingIcon = {
                    IconButton(onClick = onTogglePasswordVisibility) {
                        Icon(
                            imageVector = if (state.passwordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            },
                            contentDescription = if (state.passwordVisible) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                        )
                    }
                },
            )

            TextButton(
                onClick = onForgotPassword,
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.md))

            PrimaryButton(
                text = "Log In",
                onClick = onSubmit,
                enabled = state.canSubmit,
                isLoading = state.isLoading,
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Preview(name = "Login", showBackground = true)
@Composable
private fun LoginPreview() {
    LendlyTheme {
        LoginContent(
            state = LoginUiState(phone = "11 2345 6789", password = "secret"),
            onPhoneChange = {},
            onPasswordChange = {},
            onCountrySelected = {},
            onTogglePasswordVisibility = {},
            onSubmit = {},
            onForgotPassword = {},
            onNavigateToRegister = {},
            onBack = {},
        )
    }
}

@Preview(name = "Login - Error", showBackground = true)
@Composable
private fun LoginErrorPreview() {
    LendlyTheme {
        LoginContent(
            state = LoginUiState(
                phone = "11",
                password = "123",
                phoneError = "El número de teléfono no es válido",
                passwordError = "La contraseña debe tener al menos 6 caracteres",
            ),
            onPhoneChange = {},
            onPasswordChange = {},
            onCountrySelected = {},
            onTogglePasswordVisibility = {},
            onSubmit = {},
            onForgotPassword = {},
            onNavigateToRegister = {},
            onBack = {},
        )
    }
}
