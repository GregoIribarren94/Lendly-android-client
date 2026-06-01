package com.lendly.fintech.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SnackbarController
import com.lendly.fintech.data.auth.FirebaseAuthManager
import com.lendly.fintech.data.common.NetworkErrorType
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
 * Estado de la pantalla de Login expuesto a la UI.
 */
data class LoginUiState(
    val password: String = "",
    val passwordVisible: Boolean = false,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    // Perfil predeterminado: el teléfono ya viene seleccionado, no se ingresa en esta pantalla.
    val profileInitials: String = "JD",
    val profileName: String = "John Doe",
    val profilePhone: String = "+63-923456790",
) {
    /** Habilita el botón solo cuando hay contraseña y no hay envío en curso. */
    val canSubmit: Boolean
        get() = password.isNotBlank() && !isLoading
}

/**
 * ViewModel de la pantalla de Login (MVVM + StateFlow).
 *
 * Valida la contraseña antes de llamar a `POST /auth/login` con el teléfono predeterminado del
 * perfil; ante un 200 con token lo persiste en [SessionManager] y marca [LoginUiState.isLoggedIn]
 * para que la UI navegue a Home. Los errores (salvo 401, que maneja el bus de sesión) se muestran
 * en el Snackbar global.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val snackbarController: SnackbarController,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, passwordError = null) }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    /** Valida la contraseña y, si es válida, inicia sesión con el teléfono predeterminado. */
    fun onSubmit() {
        val state = _uiState.value
        if (state.isLoading) return

        val passwordError = validatePassword(state.password)
        if (passwordError != null) {
            _uiState.update { it.copy(passwordError = passwordError) }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val phone = state.profilePhone.filter { it.isDigit() }
            when (val result = authRepository.login("+$phone", state.password)) {
                is Resource.Success -> {
                    sessionManager.saveToken(result.data.token)
                    // Sesión de Firebase "aparte", best-effort: no bloquea el login si falla.
                    firebaseAuthManager.ensureSignedIn()
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

    private fun validatePassword(password: String): String? = when {
        password.isEmpty() -> "Ingresá tu contraseña"
        password.length < MIN_PASSWORD_LENGTH -> "La contraseña debe tener al menos $MIN_PASSWORD_LENGTH caracteres"
        else -> null
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}
