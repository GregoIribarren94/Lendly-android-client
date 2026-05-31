package com.lendly.fintech.ui.screens.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SessionEventBus
import com.lendly.fintech.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de la pantalla de gestión.
 *
 * Expone el logout: borra el token de sesión con [SessionManager] y notifica al
 * [SessionEventBus] para que la UI raíz (LendlyAppRoot) redirija a Login.
 */
@HiltViewModel
class ManageViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val sessionEventBus: SessionEventBus,
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            sessionManager.clear()
            sessionEventBus.notifySessionExpired()
        }
    }
}
