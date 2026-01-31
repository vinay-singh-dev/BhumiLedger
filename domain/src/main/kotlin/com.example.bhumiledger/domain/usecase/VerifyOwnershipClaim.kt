package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult

class VerifyOwnershipClaim(
    private val claimRepository: ClaimRepository
) {

    operator fun invoke(
        claimId: String,
        string: String
    ): DomainResult<OwnershipClaim> {
        val claim = claimRepository.getClaimById(claimId)
            ?: return DomainResult.Failure(DomainError.ClaimNotFound)

        if (claim.status != ClaimStatus.PENDING) {
            return DomainResult.Failure(DomainError.InvalidClaimState)
        }

        val verifiedClaim = claim.copy(
            status = ClaimStatus.VERIFIED
        )

        claimRepository.updateClaim(verifiedClaim)

        return DomainResult.Success(verifiedClaim)

    }
}
