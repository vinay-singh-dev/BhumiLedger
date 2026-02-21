package com.example.bhumiledger.data.repository

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository

class InMemoryClaimRepository: ClaimRepository {

    private val claims = mutableListOf<OwnershipClaim>()
    override suspend  fun getPendingClaimForParcel(parcelId: String): OwnershipClaim? {
        return claims.find{ it.parcelId == parcelId && it.status == ClaimStatus.PENDING}

    }

    override suspend fun saveClaim(claim: OwnershipClaim) {
        claims.add(claim)
    }

    override suspend fun getClaimById(claimId: String): OwnershipClaim? {
       for (claim in claims)
           if(claim.id == claimId)
               return claim
        return null
    }

    override suspend fun updateClaim(claim: OwnershipClaim) {
        val index = claims.indexOfFirst { it.id == claim.id}
        if(index != -1)
            claims[index] = claim

    }

    override suspend fun getAllPendingClaims(): List<OwnershipClaim> {
        return claims.filter { it.status == ClaimStatus.PENDING }
    }
}