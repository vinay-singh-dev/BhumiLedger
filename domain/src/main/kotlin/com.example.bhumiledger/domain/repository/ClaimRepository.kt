package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import kotlinx.coroutines.flow.Flow

interface ClaimRepository {
   suspend fun getPendingClaimForParcel(parcelId: String): OwnershipClaim?
    suspend fun saveClaim(claim: OwnershipClaim)
    suspend fun getClaimById(claimId: String): OwnershipClaim?
    suspend fun updateClaim(claim: OwnershipClaim)
    suspend fun getAllPendingClaims(): List<OwnershipClaim>
     fun getClaimsByUser(userId: String): Flow<List<OwnershipClaim>>
    suspend fun updateSyncState(claimId :String , state: SyncState)
    suspend fun getPendingSyncClaims():List <OwnershipClaim>

}