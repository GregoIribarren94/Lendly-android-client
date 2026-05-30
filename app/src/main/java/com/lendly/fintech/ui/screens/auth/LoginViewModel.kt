package com.lendly.fintech.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SnackbarController
import com.lendly.fintech.data.common.NetworkErrorType
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.local.SessionManager
import com.lendly.fintech.data.repository.AuthRepository
import com.lendly.fintech.ui.components.inputs.Country
import com.lendly.fintech.ui.components.inputs.defaultCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado de la pantalla de Login expuesto a la UI.
 */
data class LoginUiState(
    val phone: String = "",
    val password: String = "",
    val country: Country = defaultCountries.first(),
    val passwordVisible: Boolean = false,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
) {
    /** Habilita el botón solo cuando ambos campos tienen contenido y no hay envío en curso. */
    val canSubmit: Boolean
        get() = phone.isNotBlank() && password.isNotBlank() && !isLoading
}

/**
 * ViewModel de la pantalla de Login (MVVM + StateFlow).
 *
 * Valida el formulario antes de llamar a `POST /auth/login`; ante un 200 con token lo
 * persiste en [SessionManager] y marca [LoginUiState.isLoggedIn] para que la UI navegue a Home.
 * Los errores (salvo 401, que maneja el bus de sesión) se muestran en el Snackbar global.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
    private val snackbarController: SnackbarController,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onPhoneChange(value: String) {
        _uiState.update { it.copy(phone = value, phoneError = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, passwordError = null) }
    }

    fun onCountrySelected(country: Country) {
        _uiState.update { it.copy(country = country) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    /** Valida y, si el formulario es válido, inicia sesión. */
    fun onSubmit() {
        val state = _uiState.value
        if (state.isLoading) return

        val phoneError = validatePhone(state.phone)
        val passwordError = validatePassword(state.password)
        if (phoneError != null || passwordError != null) {
            _uiState.update { it.copy(phoneError = phoneError, passwordError = passwordError) }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val fullPhone = "+${state.country.dialCode}${state.phone.filter(Char::isDigit)}"
            when (val result = authRepository.login(fullPhone, state.password)) {
                is Resource.Success -> {
                    sessionManager.saveToken(result.data.token)
                    _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
                }
                is Resource.Error -> {
                    if (result.type != NetworkErrorType.UNAUTHORIZED) {
                        snackbarController.show(result.message)
                    }
                    _uiState.update { it.copy(isLoading = false) }
                }
                Resource.Loading -> Unit
            }
        }
    }

    private fun validatePhone(phone: String): String? {
        val digits = phone.filter(Char::isDigit)
        return when {
            digits.isEmpty() -> "Ingresá tu número de teléfono"
            digits.length < MIN_PHONE_DIGITS -> "El número de teléfono no es válido"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? = when {
        password.isEmpty() -> "Ingresá tu contraseña"
        password.length < MIN_PASSWORD_LENGTH -> "La contraseña debe tener al menos $MIN_PASSWORD_LENGTH caracteres"
        else -> null
    }

    private companion object {
        const val MIN_PHONE_DIGITS = 6
        const val MIN_PASSWORD_LENGTH = 6
    }
}