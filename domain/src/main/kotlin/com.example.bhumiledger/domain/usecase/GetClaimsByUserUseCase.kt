package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow


class GetClaimsByUserUseCase(
    private val claimRepository: ClaimRepository
) {
    operator fun invoke(userId: String): Flow<List<OwnershipClaim>> {
        return claimRepository.getClaimsByUser(userId)

    }
}