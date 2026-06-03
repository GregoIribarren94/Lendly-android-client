package com.lendly.fintech.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SnackbarController
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.local.SessionManager
import com.lendly.fintech.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Datos acumulados durante el flujo de registro (VerifyPhone → SmsVerification →
 * ProfileForm → CreatePassword). Cuando el usuario confirma la contraseña, este
 * estado se envía como `CreateUserRequest` a `POST /auth/create`.
 */
data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
) {
    val passwordMeetsRequirements: Boolean
        get() = password.length >= MIN_PASSWORD_LENGTH &&
            password.any(Char::isLetter) &&
            password.any(Char::isDigit)

    val canSubmit: Boolean
        get() = passwordMeetsRequirements && !isLoading

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}

/**
 * ViewModel scoped al sub-graph de registro: comparte los datos ingresados
 * paso a paso y finalmente llama a `POST /auth/create` desde CreatePasswordScreen.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
    private val snackbarController: SnackbarController,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onPhoneEntered(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun onProfileEntered(name: String, lastName: String, email: String) {
        _uiState.update { it.copy(name = name, lastName = lastName, email = email) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    /** Envía el `CreateUserRequest` y, si es exitoso, persiste el token. */
    fun onSubmit() {
        val state = _uiState.value
        if (!state.canSubmit) return

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = authRepository.register(
                name = state.name,
                lastName = state.lastName,
                email = state.email,
                phone = state.phone,
                password = state.password,
            )
            when (result) {
                is Resource.Success -> {
                    sessionManager.saveToken(result.data.token)
                    _uiState.update { it.copy(isLoading = false, isRegistered = true) }
                }
                is Resource.Error -> {
                    snackbarController.show(result.message)
                    _uiState.update { it.copy(isLoading = false) }
                }
                Resource.Loading -> Unit
            }
        }
    }
}
