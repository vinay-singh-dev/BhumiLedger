package domain.repository

import domain.model.OwnershipClaim

interface ClaimRepository {
    fun getPendingClaimForParcel(parcelId: String): OwnershipClaim?
    fun saveClaim(claim: OwnershipClaim)
}