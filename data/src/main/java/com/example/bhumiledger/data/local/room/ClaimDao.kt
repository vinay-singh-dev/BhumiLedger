package com.example.bhumiledger.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bhumiledger.domain.model.SyncState

@Dao
interface ClaimDao {

    @Insert
    suspend fun insert(claim: ClaimEntity)

    @Update
    suspend fun update(claim: ClaimEntity)

    // Used by business layer
    @Query("SELECT * FROM claims WHERE claimId = :claimId LIMIT 1")
    suspend fun getById(claimId: String): ClaimEntity?

    // Used to prevent duplicate pending claims
    @Query("SELECT * FROM claims WHERE parcelId = :parcelId AND status = 'PENDING' LIMIT 1")
    suspend fun getPendingClaim(parcelId: String): ClaimEntity?

    // CRITICAL: Used internally to fetch correct primary key for update
    @Query("SELECT * FROM claims WHERE claimId = :claimId LIMIT 1")
    suspend fun getEntityByClaimId(claimId: String): ClaimEntity?

    @Query("SELECT * FROM claims WHERE status = 'PENDING'")
    suspend fun getAllPending(): List<ClaimEntity>

    @Query("SELECT * FROM claims WHERE claimantId = :userId")
    suspend fun getByUserId(userId: String): List<ClaimEntity>

    @Query ("UPDATE claims SET syncState = :state WHERE claimId = :claimId")
    suspend fun updateSyncState(claimId: String , state: SyncState)

    @Query("SELECT * FROM claims WHERE syncState IN ('PENDING', 'FAILED')")
    suspend fun getPendingSyncClaims(): List<ClaimEntity>
}
