package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult

class RejectOwnershipClaim(
    private val claimRepository: ClaimRepository
) {

    suspend operator fun invoke(
        claimId: String,
        role: UserRole
    ): DomainResult<OwnershipClaim> {

        if (role != UserRole.AUTHORITY) {
            return DomainResult.Failure(DomainError.UnauthorizedAccess)
        }

        val claim = claimRepository.getClaimById(claimId)
            ?: return DomainResult.Failure(DomainError.ClaimNotFound)

        if (claim.status != ClaimStatus.PENDING) {
            return DomainResult.Failure(DomainError.InvalidClaimState)
        }

        val rejected = claim.copy(status = ClaimStatus.REJECTED)

        claimRepository.updateClaim(rejected)

        return DomainResult.Success(rejected)
    }
}