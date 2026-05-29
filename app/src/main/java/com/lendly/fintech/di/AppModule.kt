package com.lendly.fintech.di

import com.lendly.fintech.data.api.AuthApi
import com.lendly.fintech.data.api.LoansApi
import com.lendly.fintech.data.api.ProductsApi
import com.lendly.fintech.data.api.TransactionsApi
import com.lendly.fintech.data.api.UserApi
import com.lendly.fintech.data.repository.AuthRepository
import com.lendly.fintech.data.repository.AuthRepositoryImpl
import com.lendly.fintech.data.repository.LoanRepository
import com.lendly.fintech.data.repository.LoanRepositoryImpl
import com.lendly.fintech.data.repository.ProductRepository
import com.lendly.fintech.data.repository.ProductRepositoryImpl
import com.lendly.fintech.data.repository.TransactionRepository
import com.lendly.fintech.data.repository.TransactionRepositoryImpl
import com.lendly.fintech.data.repository.UserRepository
import com.lendly.fintech.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt que provee los repositorios de la app.
 *
 * Cada repositorio se construye sobre la Api correspondiente (provista por
 * `NetworkModule`) y se expone como su tipo interfaz para desacoplar a los
 * ViewModels de las implementaciones concretas.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository =
        AuthRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi): UserRepository =
        UserRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideLoanRepository(api: LoansApi): LoanRepository =
        LoanRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTransactionRepository(api: TransactionsApi): TransactionRepository =
        TransactionRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductsApi): ProductRepository =
        ProductRepositoryImpl(api)
}