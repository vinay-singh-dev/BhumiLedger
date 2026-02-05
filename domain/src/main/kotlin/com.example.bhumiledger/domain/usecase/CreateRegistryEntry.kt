package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult

class CreateRegistryEntry(
    private val registryRepository: RegistryRepository
) {
    operator fun invoke(claim: OwnershipClaim): DomainResult<RegistryEntry> {


        if (claim.status != ClaimStatus.VERIFIED) {
            return DomainResult.Failure(DomainError.InvalidClaimState)
        }

        if(registryRepository.getByParcelId(claim.parcelId) != null)
            return DomainResult.Failure(DomainError.OwnershipAlreadyExists)

        val entry = RegistryEntry(
            parcelId = claim.parcelId,
            ownerId = claim.claimantId,
            createdAt = System.currentTimeMillis()
        )

        registryRepository.save(entry)

        return DomainResult.Success(entry)
    }
}

