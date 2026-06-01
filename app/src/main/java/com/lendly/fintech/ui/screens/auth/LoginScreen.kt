package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.R



@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onChangeProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }

    LoginContent(
        state = uiState,
        onPasswordChange = viewModel::onPasswordChange,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onSubmit = viewModel::onSubmit,
        onForgotPassword = onForgotPassword,
        onChangeProfile = onChangeProfile,
        modifier = modifier
    )
}

@Composable
private fun LoginContent(
    state: LoginUiState,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmit: () -> Unit,
    onForgotPassword: () -> Unit,
    onChangeProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Bloque Superior Contenedor
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(180.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_simbolo),
                    contentDescription = "Lendly",
                    modifier = Modifier.size(width = 180.dp, height = 110.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(48.dp))

                SelectedProfileCard(
                    initials = state.profileInitials.ifEmpty { "JD" },
                    name = state.profileName.ifEmpty { "John Doe" },
                    phone = state.profilePhone.ifEmpty { "+63-923456790" },
                    onChange = onChangeProfile,
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Bloque contenedor Password
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Password",
                        color = Color(0xFF454745),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    AppTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        label = "",
                        placeholder = "123123123",
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
                                    contentDescription = null,
                                    tint = Color(0xFF777777)
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    TextButton(
                        onClick = onForgotPassword,
                        modifier = Modifier.height(18.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Forgot your password?",
                            fontSize = 11.sp,
                            color = Color(0xFF355B21),
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            } // Cierre correcto del Column superior

            // Bloque Inferior Fijo con el botón y la línea divisoria
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE0E0E0))
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryButton(
                    text = "Log In",
                    onClick = onSubmit,
                    enabled = state.canSubmit,
                    isLoading = state.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            Color(0xFF76EE76),
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun SelectedProfileCard(
    initials: String,
    name: String,
    phone: String,
    onChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFF4F4F4)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = initials,
                fontSize = 11.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = name,
                fontSize = 13.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Text(
                text = phone,
                fontSize = 10.sp,
                color = Color(0xFF777777),
                maxLines = 1
            )
        }

        TextButton(
            onClick = onChange,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.height(24.dp)
        ) {
            Text(
                text = "Change",
                fontSize = 11.sp,
                color = Color(0xFF355B21),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium
            )
        }
    }
}