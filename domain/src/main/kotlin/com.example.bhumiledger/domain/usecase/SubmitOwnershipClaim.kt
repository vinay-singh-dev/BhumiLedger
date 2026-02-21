package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import java.util.UUID

class SubmitOwnershipClaim(
    private val claimRepository: ClaimRepository,
    private val registryRepository: RegistryRepository
) {

    suspend operator fun invoke(
        parcelId: String,
        claimantId: String
    ): DomainResult<OwnershipClaim> {

        if (parcelId.isBlank() || claimantId.isBlank()) {
            return DomainResult.Failure(
                DomainError.InvalidInput
            )
        }

        // CRITICAL: prevent claim if parcel already owned
        val existingOwner =
            registryRepository.getByParcelId(parcelId)

        if (existingOwner != null) {
            return DomainResult.Failure(
                DomainError.OwnershipAlreadyExists
            )
        }

        // prevent duplicate pending claim
        val existingPending =
            claimRepository.getPendingClaimForParcel(parcelId)

        if (existingPending != null) {
            return DomainResult.Failure(
                DomainError.DuplicatePendingClaim
            )
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
