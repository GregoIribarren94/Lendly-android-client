package com.lendly.fintech.di

import android.content.Context
import androidx.room.Room
import com.lendly.fintech.data.local.db.LendlyDatabase
import com.lendly.fintech.data.local.db.dao.LoanDao
import com.lendly.fintech.data.local.db.dao.TransactionDao
import com.lendly.fintech.data.local.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provee la base de datos Room [LendlyDatabase] y sus DAOs como singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LendlyDatabase =
        Room.databaseBuilder(context, LendlyDatabase::class.java, "lendly.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(db: LendlyDatabase): UserDao = db.userDao()

    @Provides
    fun provideLoanDao(db: LendlyDatabase): LoanDao = db.loanDao()

    @Provides
    fun provideTransactionDao(db: LendlyDatabase): TransactionDao = db.transactionDao()
}