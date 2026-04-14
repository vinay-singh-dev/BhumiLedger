package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState

interface ClaimRepository {
   suspend fun getPendingClaimForParcel(parcelId: String): OwnershipClaim?
    suspend fun saveClaim(claim: OwnershipClaim)
    suspend fun getClaimById(claimId: String): OwnershipClaim?
    suspend fun updateClaim(claim: OwnershipClaim)
    suspend fun getAllPendingClaims(): List<OwnershipClaim>
    suspend fun getClaimsByUser(userId: String): List<OwnershipClaim>
    suspend fun updateSyncState(claimId :String , state: SyncState)
    suspend fun getPendingSyncClaims():List <OwnershipClaim>

}