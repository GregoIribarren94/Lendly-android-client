package com.lendly.fintech.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SessionEventBus
import com.lendly.fintech.core.SnackbarController
import com.lendly.fintech.data.common.NetworkErrorType
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de Home. Centraliza cómo se reacciona ante un [Resource.Error]:
 * el 401 lo maneja el bus global (redirección a Login), el resto se muestra en el
 * Snackbar global con un mensaje claro y no técnico.
 *
 * Incluye acciones de demostración para verificar el criterio "Manejo de errores".
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val snackbarController: SnackbarController,
    private val sessionEventBus: SessionEventBus,
) : ViewModel() {

    /** Llamada real a la API; muestra el resultado o el error en el Snackbar global. */
    fun probarLlamadaReal() {
        viewModelScope.launch {
            when (val result = userRepository.getProfile("1")) {
                is Resource.Success ->
                    snackbarController.show("Usuario cargado correctamente")
                is Resource.Error -> handleError(result)
                Resource.Loading -> Unit
            }
        }
    }

    /**
     * Punto único de manejo de errores en la UI. El 401 no se muestra como Snackbar:
     * lo resuelve el [SessionEventBus] (interceptor) redirigiendo a Login.
     */
    private fun handleError(error: Resource.Error) {
        if (error.type == NetworkErrorType.UNAUTHORIZED) return
        snackbarController.show(error.message)
    }

    // --- Acciones de demostración (simulan cada tipo de error sin depender del backend) ---

    fun simularSinConexion() = snackbarController.show(NetworkErrorType.NO_CONNECTION.userMessage)

    fun simularTimeout() = snackbarController.show(NetworkErrorType.TIMEOUT.userMessage)

    fun simularError4xx() = snackbarController.show(NetworkErrorType.CLIENT_ERROR.userMessage)

    fun simularError5xx() = snackbarController.show(NetworkErrorType.SERVER_ERROR.userMessage)

    /** Simula un 401: dispara la redirección global a Login. */
    fun simular401() = sessionEventBus.notifySessionExpired()
}