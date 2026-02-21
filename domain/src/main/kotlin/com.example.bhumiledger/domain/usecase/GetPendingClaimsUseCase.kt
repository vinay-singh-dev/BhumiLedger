package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult

class GetPendingClaimsUseCase(
    private val claimRepository: ClaimRepository
) {
    suspend operator fun invoke(): DomainResult<List<OwnershipClaim>> {
        return try {
            val claims = claimRepository.getAllPendingClaims()
            DomainResult.Success(claims)
        } catch (e: Exception) {
            DomainResult.Failure(DomainError.InvalidInput)
        }
    }
}