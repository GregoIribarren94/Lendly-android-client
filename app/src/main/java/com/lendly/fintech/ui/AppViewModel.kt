package com.lendly.fintech.ui

import androidx.lifecycle.ViewModel
import com.lendly.fintech.core.SessionEventBus
import com.lendly.fintech.core.SnackbarController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * ViewModel raíz que expone los buses globales a la UI raíz ([LendlyAppRoot]):
 * los mensajes para el Snackbar global y los eventos de sesión expirada (401).
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    sessionEventBus: SessionEventBus,
    snackbarController: SnackbarController,
) : ViewModel() {

    val sessionExpired: SharedFlow<Unit> = sessionEventBus.sessionExpired
    val messages: SharedFlow<String> = snackbarController.messages
}