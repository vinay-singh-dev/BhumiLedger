package com.example.bhumiledger.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BlockDao {

    @Query("SELECT * FROM blocks ORDER BY `index` DESC LIMIT 1")
    suspend fun getLast(): BlockEntity?

    @Insert
    suspend fun insert(block: BlockEntity)

    @Query("SELECT * FROM blocks")
    suspend fun getAll(): List<BlockEntity>
}