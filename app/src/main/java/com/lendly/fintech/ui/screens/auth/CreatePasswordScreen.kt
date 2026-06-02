package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.BackgroundCard
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentTertiary
import com.lendly.fintech.ui.theme.FormHelper
import com.lendly.fintech.ui.theme.FormLabel
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

/**
 * Última pantalla del flujo de registro: el usuario elige su contraseña.
 *
 * Cuando la contraseña cumple los requisitos (mínimo 9 caracteres con al menos
 * una letra y un número), `Next` dispara `POST /auth/create` a través de
 * [RegisterViewModel] y navega a Done si la API responde 2xx.
 */
@Composable
fun CreatePasswordScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) onContinue()
    }

    CreatePasswordContent(
        state = uiState,
        onPasswordChange = viewModel::onPasswordChange,
        onToggleVisibility = viewModel::onTogglePasswordVisibility,
        onSubmit = viewModel::onSubmit,
        onBack = onBack,
    )
}

@Composable
private fun CreatePasswordContent(
    state: RegisterUiState,
    onPasswordChange: (String) -> Unit,
    onToggleVisibility: () -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundCard,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AuthTopBar(
                onBack = onBack,
                onInfo = {},
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.lg),
            ) {
                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = "Create your password",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ContentPrimary,
                )

                Spacer(modifier = Modifier.height(Spacing.lg))

                Text(
                    text = "Choose a password",
                    style = FormLabel,
                    color = ContentPrimary,
                )

                Spacer(modifier = Modifier.height(Spacing.xs))

                PasswordField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    visible = state.passwordVisible,
                    onToggleVisibility = onToggleVisibility,
                )

                Spacer(modifier = Modifier.height(Spacing.xs))

                Text(
                    text = passwordHint(),
                    style = FormHelper,
                    color = ContentPrimary,
                )
            }

            AuthBottomBar(
                text = "Next",
                onClick = onSubmit,
                enabled = state.canSubmit,
                isLoading = state.isLoading,
            )
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onToggleVisibility: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = {
            Text(
                text = "********",
                color = ContentTertiary // O el color gris claro que tengas definido para las pistas
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ContentTertiary,
            unfocusedBorderColor = ContentTertiary,
            disabledBorderColor = ContentTertiary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                if (visible) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Ocultar contraseña",
                        tint = ContentTertiary,
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visibility_off),
                        contentDescription = "Mostrar contraseña",
                        tint = Color.Unspecified,
                    )
                }
            }
        },
    )
}

private fun passwordHint() = buildAnnotatedString {
    append("At least ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("9 characters") }
    append(", containing ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("a letter") }
    append(" and ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("a number") }
}

@Preview(name = "CreatePassword - Empty", showBackground = true)
@Composable
private fun CreatePasswordEmptyPreview() {
    LendlyTheme {
        CreatePasswordContent(
            state = RegisterUiState(),
            onPasswordChange = {},
            onToggleVisibility = {},
            onSubmit = {},
            onBack = {},
        )
    }
}

@Preview(name = "CreatePassword - Valid", showBackground = true)
@Composable
private fun CreatePasswordValidPreview() {
    LendlyTheme {
        CreatePasswordContent(
            state = RegisterUiState(password = "lendly123"),
            onPasswordChange = {},
            onToggleVisibility = {},
            onSubmit = {},
            onBack = {},
        )
    }
}

@Preview(name = "CreatePassword - Visible", showBackground = true)
@Composable
private fun CreatePasswordVisiblePreview() {
    LendlyTheme {
        CreatePasswordContent(
            state = RegisterUiState(password = "lendly123", passwordVisible = true),
            onPasswordChange = {},
            onToggleVisibility = {},
            onSubmit = {},
            onBack = {},
        )
    }
}
