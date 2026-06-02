package com.lendly.fintech.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.lendly.fintech.data.local.db.entities.LoanEntity

@Dao
interface LoanDao {

    @Query("SELECT * FROM loans")
    suspend fun getAll(): List<LoanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(loans: List<LoanEntity>)

    @Query("DELETE FROM loans")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(loans: List<LoanEntity>) {
        deleteAll()
        insertAll(loans)
    }
}
