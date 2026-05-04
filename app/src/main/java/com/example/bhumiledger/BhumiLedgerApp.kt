package com.example.bhumiledger

import android.app.Application
import com.example.bhumiledger.data.local.room.DatabaseProvider
import com.example.bhumiledger.data.repository.RoomClaimRepository
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.usecase.SyncClaimsUseCase
import com.example.bhumiledger.data.remote.firestore.FirestoreDataSource

class BhumiLedgerApp: Application() {

    lateinit var claimRepository: ClaimRepository
    lateinit var syncClaimsUseCase: SyncClaimsUseCase
    lateinit var syncScheduler: SyncScheduler

    override fun onCreate() {
        super.onCreate()

        val db = DatabaseProvider.getDatabase(this)
        val firestore = FirestoreDataSource()

        claimRepository = RoomClaimRepository(
            db.claimDao(),
            firestore

        )
        syncClaimsUseCase = SyncClaimsUseCase(claimRepository)
        syncScheduler = SyncScheduler(this)
    }

}