package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult
import java.util.UUID


class SubmitOwnershipClaim(
    private val claimRepository: ClaimRepository
) {
    operator fun invoke(
        parcelId: String,
        claimantId: String
    ): DomainResult<OwnershipClaim> {

        val existing = claimRepository.getPendingClaimForParcel(parcelId)
        if (existing != null) {
            return DomainResult.Failure(DomainError.DuplicatePendingClaim)
        }

        val claim = OwnershipClaim(
            id = UUID.randomUUID().toString(),
            parcelId = parcelId,
            claimantId = claimantId,
            status = ClaimStatus.PENDING
        )

        claimRepository.saveClaim(claim)
        return DomainResult.Success(claim)
    }
}
