package com.lendly.fintech.data.remote

import com.google.firebase.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Módulo Hilt que provee la pila de red: OkHttp + Retrofit + Gson.
 *
 * - Inyecta el header obligatorio `x-api-key` en todas las peticiones.
 * - Habilita el logging de cuerpo/headers solo en builds debug.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_KEY_HEADER = "x-api-key"
    private const val API_KEY_VALUE = "123456789"
    private const val TIMEOUT_SECONDS = 30L

    /** Interceptor que agrega el header obligatorio a cada request. */
    @Provides
    @Singleton
    @ApiKeyInterceptor
    fun provideApiKeyInterceptor(): okhttp3.Interceptor = okhttp3.Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader(API_KEY_HEADER, API_KEY_VALUE)
            .build()
        chain.proceed(request)
    }

    /** Interceptor de logging, activo únicamente en debug. */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApiKeyInterceptor apiKeyInterceptor: okhttp3.Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://6d710e79-f4ca-4651-909f-7dd13bd29968.mock.pstmn.io/") // <-- PARCHE TEMPORAL EN STRING
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}
