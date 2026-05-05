package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.SyncState
import com.example.bhumiledger.domain.repository.ClaimRepository

class SyncClaimsUseCase(
    private val repository: ClaimRepository
) {

    suspend operator fun invoke(): Boolean {

        val pendingClaims = repository.getPendingSyncClaims()

        var hasFailure = false

        for (claim in pendingClaims) {
            try {
                val result = repository.pushToFirestore(claim)

                if (result.isFailure) {
                    hasFailure = true
                }
            } catch (e: Exception) {
                repository.updateSyncState(claim.id, SyncState.FAILED)
                hasFailure = true
            }
        }
        return hasFailure
    }
}
