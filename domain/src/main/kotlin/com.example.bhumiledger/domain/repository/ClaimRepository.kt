package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.OwnershipClaim

interface ClaimRepository {
   suspend fun getPendingClaimForParcel(parcelId: String): OwnershipClaim?
    suspend fun saveClaim(claim: OwnershipClaim)
    suspend fun getClaimById(claimId: String): OwnershipClaim?
    suspend fun updateClaim(claim: OwnershipClaim)
    suspend fun getAllPendingClaims(): List<OwnershipClaim>
    suspend fun getClaimsByUser(userId: String): List<OwnershipClaim>

}