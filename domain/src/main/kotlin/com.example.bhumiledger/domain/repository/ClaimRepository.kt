package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.OwnershipClaim

interface ClaimRepository {
    fun getPendingClaimForParcel(parcelId: String): OwnershipClaim?
    fun saveClaim(claim: OwnershipClaim)
    fun getClaimById(claimId: String): OwnershipClaim?
    fun updateClaim(claim: OwnershipClaim)

}