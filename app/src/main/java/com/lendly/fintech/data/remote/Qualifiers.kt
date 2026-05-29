package com.lendly.fintech.data.remote

import javax.inject.Qualifier

/** Distingue el interceptor que agrega el header `x-api-key`. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptor
