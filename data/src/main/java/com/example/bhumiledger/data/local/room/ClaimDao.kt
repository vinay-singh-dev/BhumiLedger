package com.example.bhumiledger.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClaimDao {

    @Insert
    suspend fun insert(claim: ClaimEntity)

    @Update
    suspend fun update(claim: ClaimEntity)

    @Query("SELECT * FROM claims WHERE claimId = :claimId LIMIT 1")
    suspend fun getById(claimId: String): ClaimEntity?

    @Query("SELECT * FROM claims WHERE parcelId = :parcelId AND status = 'PENDING' LIMIT 1")
    suspend fun getPendingClaim(parcelId: String): ClaimEntity?
}

