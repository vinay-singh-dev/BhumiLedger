package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult

class VerifyOwnershipClaim(
    private val claimRepository: ClaimRepository,
    private val registryRepository: RegistryRepository
) {

    operator fun invoke(
        claimId: String,
        role: UserRole
    ): DomainResult<OwnershipClaim> {

        // CRITICAL: Only authority can verify
        if (role != UserRole.AUTHORITY) {
            return DomainResult.Failure(
                DomainError.UnauthorizedAccess
            )
        }

        if (claimId.isBlank()) {
            return DomainResult.Failure(DomainError.InvalidInput)
        }

        val claim =
            claimRepository.getClaimById(claimId)
                ?: return DomainResult.Failure(
                    DomainError.ClaimNotFound
                )

        if (claim.status != ClaimStatus.PENDING) {
            return DomainResult.Failure(
                DomainError.InvalidClaimState
            )
        }

        // Mark verified
        val verifiedClaim =
            claim.copy(
                status = ClaimStatus.VERIFIED
            )

        claimRepository.updateClaim(
            verifiedClaim
        )

        // Create registry entry automatically
        registryRepository.save(
            com.example.bhumiledger.domain.model.RegistryEntry(
                parcelId = verifiedClaim.parcelId,
                ownerId = verifiedClaim.claimantId,
                createdAt = System.currentTimeMillis()
            )
        )

        return DomainResult.Success(
            verifiedClaim
        )
    }
}
