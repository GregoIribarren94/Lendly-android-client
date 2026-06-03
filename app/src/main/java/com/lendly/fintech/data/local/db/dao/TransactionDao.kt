package com.lendly.fintech.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.lendly.fintech.data.local.db.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionEntity>

    @Query("DELETE FROM transactions")
    suspend fun clear()

    /** Reemplaza el cache completo de movimientos en una sola transacción. */
    @Transaction
    suspend fun replaceAll(transactions: List<TransactionEntity>) {
        clear()
        upsertAll(transactions)
    }
}