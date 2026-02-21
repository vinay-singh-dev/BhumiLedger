package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult

class GetClaimsByUserUseCase(
    private val claimRepository: ClaimRepository
) {
    suspend operator fun invoke(userId: String): DomainResult<List<OwnershipClaim>> {
        return DomainResult.Success(
            claimRepository.getClaimsByUser(userId)
        )
    }
}