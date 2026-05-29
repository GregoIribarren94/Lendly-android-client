package com.lendly.fintech.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Bus global de eventos de sesión.
 *
 * Permite que cualquier capa (por ejemplo, el interceptor que detecta un HTTP 401)
 * notifique que la sesión expiró. La UI raíz observa [sessionExpired] y redirige a Login.
 */
@Singleton
class SessionEventBus @Inject constructor() {

    // extraBufferCapacity = 1 permite emitir desde contextos no suspendidos (tryEmit)
    // sin perder el evento aunque todavía no haya colectores activos.
    private val _sessionExpired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val sessionExpired: SharedFlow<Unit> = _sessionExpired.asSharedFlow()

    /** Notifica que la sesión expiró (401). Seguro de llamar desde cualquier hilo. */
    fun notifySessionExpired() {
        _sessionExpired.tryEmit(Unit)
    }
}