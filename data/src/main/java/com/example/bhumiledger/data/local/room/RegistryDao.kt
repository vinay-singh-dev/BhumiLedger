package com.example.bhumiledger.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RegistryDao {

    @Insert
    suspend fun insert(entry: RegistryEntryEntity)

    @Query("SELECT * FROM registry_entries WHERE parcelId = :parcelId ORDER BY createdAt ASC")
    suspend fun getHistoryForParcel(parcelId: String): List<RegistryEntryEntity>

    @Query("SELECT * FROM registry_entries WHERE parcelId = :parcelId ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestForParcel(parcelId: String): RegistryEntryEntity?
}
