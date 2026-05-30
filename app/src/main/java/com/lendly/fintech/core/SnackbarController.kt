package com.lendly.fintech.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Punto único para mostrar mensajes en el Snackbar global de la app.
 *
 * Cualquier capa (ViewModels, etc.) publica un mensaje con [show] y la UI raíz,
 * que observa [messages], lo muestra en su único [androidx.compose.material3.SnackbarHost].
 */
@Singleton
class SnackbarController @Inject constructor() {

    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val messages: SharedFlow<String> = _messages.asSharedFlow()

    /** Encola un mensaje (claro y no técnico) para mostrarlo en el Snackbar global. */
    fun show(message: String) {
        _messages.tryEmit(message)
    }
}