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
        claimantId: String,
        documentPath:String?
    ): DomainResult<OwnershipClaim> {

        if (parcelId.isBlank() || claimantId.isBlank()) {
            return DomainResult.Failure(
                DomainError.InvalidInput
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

        val existingOwner = registryRepository.getByParcelId(parcelId)

        if (existingOwner != null && existingOwner.ownerId == claimantId) {
            return DomainResult.Failure(DomainError.AlreadyCurrentOwner)
        }

        val claim = OwnershipClaim(
            id = UUID.randomUUID().toString(),
            parcelId = parcelId,
            claimantId = claimantId,
            status = ClaimStatus.PENDING,
            documentPath = documentPath
        )

        claimRepository.saveClaim(claim)

        return DomainResult.Success(claim)
    }
}
