package com.lendly.fintech.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lendly.fintech.data.local.db.dao.LoanDao
import com.lendly.fintech.data.local.db.dao.TransactionDao
import com.lendly.fintech.data.local.db.dao.UserDao
import com.lendly.fintech.data.local.db.entity.LoanEntity
import com.lendly.fintech.data.local.db.entity.TransactionEntity
import com.lendly.fintech.data.local.db.entity.UserEntity

/**
 * Base de datos Room de la app: cache local del perfil, préstamos y movimientos.
 */
@Database(
    entities = [UserEntity::class, LoanEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class LendlyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun loanDao(): LoanDao
    abstract fun transactionDao(): TransactionDao
}