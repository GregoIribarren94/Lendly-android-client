package com.lendly.fintech.ui.screens.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SessionEventBus
import com.lendly.fintech.data.auth.FirebaseAuthManager
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.local.SessionManager
import com.lendly.fintech.data.local.db.LendlyDatabase
import com.lendly.fintech.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** Estado de la pantalla Manage expuesto a la UI. */
data class ManageUiState(
    val profileInitials: String = "",
    val profileImageUrl: String? = null,
)

/**
 * ViewModel de la pantalla de gestión.
 *
 * Carga el perfil del usuario para mostrar foto/iniciales en el header y expone el logout:
 * borra el token de sesión ([SessionManager]), cierra la sesión de Firebase ([FirebaseAuthManager])
 * y vacía el cache local de Room ([LendlyDatabase]), y luego notifica al [SessionEventBus]
 * para que la UI raíz (LendlyAppRoot) redirija a Login.
 */
@HiltViewModel
class ManageViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val database: LendlyDatabase,
    private val sessionEventBus: SessionEventBus,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUiState())
    val uiState: StateFlow<ManageUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // runCatching defensivo: el mock actual devuelve un wrapper {success, user:{...}}
            // que no matchea con data.model.User, así Gson deja campos non-null en null y el
            // mapper User.toEntity() puede tirar NPE. Hasta que se alinee el contrato del
            // modelo con el mock, evitamos crashear la pantalla.
            runCatching { userRepository.getProfile(CURRENT_USER_ID) }
                .onSuccess { res ->
                    if (res is Resource.Success) {
                        val user = res.data
                        _uiState.value = ManageUiState(
                            profileInitials = "${user.name.firstOrNull() ?: '?'}${user.lastName.firstOrNull() ?: '?'}".uppercase(),
                            profileImageUrl = user.profileImage,
                        )
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clear()
            firebaseAuthManager.signOut()
            withContext(Dispatchers.IO) { database.clearAllTables() }
            sessionEventBus.notifySessionExpired()
        }
    }

    private companion object {
        // Deuda: hardcoded mientras no exista "currentUserId" real. Mismo valor que NetworkErrorDemoViewModel.
        const val CURRENT_USER_ID = "1"
    }
}
