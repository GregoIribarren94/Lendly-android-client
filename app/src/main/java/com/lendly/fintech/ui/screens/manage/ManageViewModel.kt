package com.lendly.fintech.ui.screens.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.core.SessionEventBus
import com.lendly.fintech.data.auth.FirebaseAuthManager
import com.lendly.fintech.data.local.SessionManager
import com.lendly.fintech.data.local.db.LendlyDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel de la pantalla de gestión.
 *
 * Expone el logout: borra el token de sesión ([SessionManager]), cierra la sesión de Firebase
 * ([FirebaseAuthManager]) y vacía el cache local de Room ([LendlyDatabase]), y luego notifica al
 * [SessionEventBus] para que la UI raíz (LendlyAppRoot) redirija a Login.
 */
@HiltViewModel
class ManageViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val database: LendlyDatabase,
    private val sessionEventBus: SessionEventBus,
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            sessionManager.clear()
            firebaseAuthManager.signOut()
            withContext(Dispatchers.IO) { database.clearAllTables() }
            sessionEventBus.notifySessionExpired()
        }
    }
}