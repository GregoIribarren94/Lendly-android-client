package com.lendly.fintech.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.lendly.fintech.data.local.db.entity.LoanEntity

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(loans: List<LoanEntity>)

    @Query("SELECT * FROM loans")
    suspend fun getAll(): List<LoanEntity>

    @Query("DELETE FROM loans")
    suspend fun clear()

    /** Reemplaza el cache completo de préstamos en una sola transacción. */
    @Transaction
    suspend fun replaceAll(loans: List<LoanEntity>) {
        clear()
        upsertAll(loans)
    }
}
