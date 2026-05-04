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

//                if (claim.id.hashCode() % 2 == 0) {
//                    throw Exception("Fail some claims only")
//                }



                repository.updateSyncState(
                    claim.id,
                    SyncState.SYNCED
                )

            } catch (e: Exception) {
                repository.updateSyncState(
                    claim.id,
                    SyncState.FAILED
                )
                hasFailure = true
            }
        }

        return hasFailure
    }
}