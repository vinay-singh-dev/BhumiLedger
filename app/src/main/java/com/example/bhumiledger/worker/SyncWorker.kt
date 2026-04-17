package com.example.bhumiledger.worker

import com.example.bhumiledger.BhumiLedgerApp
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bhumiledger.domain.model.SyncState


class SyncWorker (
    context: Context,
    params:WorkerParameters
) : CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {

        Log.d("SYNC", "Worker started")

        val app = applicationContext as BhumiLedgerApp
        val useCase = app.syncClaimsUseCase


        val hasFailure = useCase()

        Log.d("SYNC", "Worker finished. hasFailure = $hasFailure")

        return if (hasFailure) {
            Log.d("SYNC", "Retry scheduled")
            Result.retry()
        } else {
            Log.d("SYNC", "All synced successfully")
            Result.success()
        }
    }
}