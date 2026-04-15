package com.example.bhumiledger.worker

import com.example.bhumiledger.BhumiLedgerApp
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bhumiledger.domain.model.SyncState


class SyncWorker (
    context: Context,
    params:WorkerParameters
) : CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {

        val app = applicationContext as BhumiLedgerApp
        val repository = app.claimRepository
        val pendingClaims = repository.getPendingSyncClaims()

        for(claim in pendingClaims) {
            try {

                repository.updateSyncState(
                    claim.id,
                    SyncState.SYNCED
                )
            } catch (e: Exception) {

                repository.updateSyncState(
                    claim.id,
                    SyncState.FAILED
                )
            }
        }
        return Result.success()

    }

}
