package com.example.bhumiledger

import android.app.Application
import android.util.Log
import com.example.bhumiledger.data.BuildConfig
import com.example.bhumiledger.data.local.room.DatabaseProvider
import com.example.bhumiledger.data.repository.RoomClaimRepository
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.usecase.SyncClaimsUseCase
import com.example.bhumiledger.data.remote.firestore.FirestoreDataSource
import com.example.bhumiledger.data.remote.storage.CloudinaryDataSource
import com.cloudinary.android.MediaManager

class BhumiLedgerApp: Application() {

    lateinit var claimRepository: ClaimRepository
    lateinit var syncClaimsUseCase: SyncClaimsUseCase
    lateinit var syncScheduler: SyncScheduler

    override fun onCreate() {
        super.onCreate()


        val config = mapOf(
            "cloud_name" to BuildConfig.CLOUDINARY_CLOUD_NAME,
            "api_key" to BuildConfig.CLOUDINARY_API_KEY,
            "api_secret" to BuildConfig.CLOUDINARY_API_SECRET
        )

        MediaManager.init(this, config)
        Log.d(
            "CLOUDINARY_TEST",
            BuildConfig.CLOUDINARY_CLOUD_NAME
        )
        val db = DatabaseProvider.getDatabase(this)
        val firestore = FirestoreDataSource()
        val storage = CloudinaryDataSource()

        claimRepository = RoomClaimRepository(
            db.claimDao(),
            firestore,
            storage

        )
        syncClaimsUseCase = SyncClaimsUseCase(claimRepository)
        syncScheduler = SyncScheduler(this)
    }

}