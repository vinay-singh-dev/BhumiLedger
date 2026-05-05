package com.example.bhumiledger

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bhumiledger.auth.toMessage
import com.example.bhumiledger.data.remote.firestore.dto.ClaimDto
import com.example.bhumiledger.domain.model.Block
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import com.example.bhumiledger.domain.result.DomainResult
import com.example.bhumiledger.ui.model.ClaimWithUser
import com.example.bhumiledger.ui.model.RegistryEntryWithUser
import com.example.bhumiledger.worker.SyncWorker
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val container: BhumiLedgerContainer,
    private val syncScheduler: SyncScheduler
) : ViewModel() {

    var status by mutableStateOf("Idle")
        private set

    var userClaims by mutableStateOf<List<OwnershipClaim>>(emptyList())
        private set

    var blockchain by mutableStateOf<List<Block>>(emptyList())
        private set

    var isChainValid by mutableStateOf(true)
        private set

    var pendingClaims by mutableStateOf<List<ClaimWithUser>>(emptyList())
    private set
    var lastClaimId by mutableStateOf("")
        private set

    var historySize by mutableStateOf(0)
        private set
    var ownershipHistory by mutableStateOf<List<RegistryEntryWithUser>>(emptyList())
        private set


    // ===============================
    // CITIZEN ACTION
    // ===============================

    private var observeJob: Job? = null

    fun observeUserClaims(userId: String) {
        observeJob?.cancel()

        observeJob = viewModelScope.launch {
            container.getClaimsByUser(userId)
                .collect { claims ->
                    userClaims = claims
                }
        }
    }

    fun loadOwnershipHistory(parcelId: String) {
        viewModelScope.launch {

            val result = container.getOwnershipHistory(parcelId)

            when (result) {

                is DomainResult.Success -> {

                    val mapped = result.data.map { entry ->

                        val owner = container.getUserById(entry.ownerId)
                        val authority = container.getUserById(entry.verifiedByAuthorityId)

                        RegistryEntryWithUser(
                            ownerName = owner?.name ?: "Unknown",
                            authorityName = authority?.name ?: "Unknown",
                            timestamp = entry.createdAt,
                            verifiedAt = entry.verifiedAt
                        )
                    }

                    ownershipHistory = mapped
                }

                is DomainResult.Failure -> {
                    status = result.error.toMessage()
                }
            }
        }
    }

    fun loadBlockchain() {
        viewModelScope.launch {
            blockchain = container.getAllBlocks()
        }
    }

    fun validateChain() {
        viewModelScope.launch {
            isChainValid = container.validateBlockchain()
        }
    }

    fun submitClaim(parcelId: String, claimantId: String,documentPath: String?) {

        viewModelScope.launch {

            Log.d("ROOM_TEST", "Citizen submitting claim")

            val result =
                container.submitOwnershipClaim(
                    parcelId,
                    claimantId,
                    documentPath
                )

            when (result) {

                is DomainResult.Success -> {

                    val claim = result.data

                    lastClaimId = claim.id

                    status = "Claim Submitted: ${claim.id}"


                    syncScheduler.scheduleSync()

                    Log.d("ROOM_TEST", status)
                }

                is DomainResult.Failure -> {

                    status = result.error.toMessage()

                    Log.e("ROOM_TEST", status)
                }
            }
        }
    }


    // ===============================
    // AUTHORITY ACTION
    // ===============================
    fun verifyClaim(claimId: String) {
        viewModelScope.launch {

            val result =
                container.verifyOwnershipClaim(
                    claimId,
                    UserRole.AUTHORITY
                )

            when (result) {

                is DomainResult.Success -> {
                    status = "Claim VERIFIED"
                    loadPendingClaims()
                }

                is DomainResult.Failure -> {
                    status = result.error.toMessage()
                }
            }
        }
    }

    fun rejectClaim(claimId: String) {
        viewModelScope.launch {

            val result =
                container.rejectOwnershipClaim(
                    claimId,
                    UserRole.AUTHORITY
                )

            when (result) {

                is DomainResult.Success -> {
                    status = "Claim REJECTED"
                    loadPendingClaims()
                }

                is DomainResult.Failure -> {
                    status = result.error.toMessage()
                }
            }
        }
    }

//    fun testFirestore() {
//        viewModelScope.launch {
//
//            val result = container.submitOwnershipClaim(
//                parcelId = "LAND123",
//                claimantId = "USER_1",
//                documentPath = null
//
//            )
//
//            when (result) {
//                is DomainResult.Success -> {
//                    Log.d("TEST", "Claim created: ${result.data.id}")
//                }
//
//                is DomainResult.Failure -> {
//                    Log.e("TEST", "Error: ${result.error}")
//
//                }
//            }
//        }
//    }


    // ===============================
    // LOAD HISTORY
    // ===============================
//    fun loadHistory(parcelId: String) {
//
//        viewModelScope.launch {
//
//            Log.d("ROOM_TEST", "Loading history")
//
//            val result =
//                container.getOwnershipHistory(
//                    parcelId
//                )
//
//            when (result) {
//
//                is DomainResult.Success -> {
//
//                    historySize = result.data.size
//
//                    status = "History entries: $historySize"
//
//                    Log.d("ROOM_TEST", status)
//                }
//
//                is DomainResult.Failure -> {
//
//                    status = result.error.toMessage()
//
//                    Log.e("ROOM_TEST", status)
//                }
//            }
//        }
//    }

    // Sync work

//    fun triggerSync(context: Context) {
//
//        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
//            .setConstraints(
//                Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build()
//            )
//            .setBackoffCriteria(
//                BackoffPolicy.EXPONENTIAL,
//                10,
//                TimeUnit.SECONDS
//            )
//            .build()
//
//        WorkManager.getInstance(context)
//            .enqueueUniqueWork(
//                "sync_claims",
//                ExistingWorkPolicy.KEEP,
//                workRequest
//            )
//    }

    fun testFirestoreDirect() {
        viewModelScope.launch {

            Log.d("TEST_FIREBASE", "Starting direct test")

            val result = container.testFirestore(
                ClaimDto(
                    claimId = "test123",
                    owner = "user1",
                    land = "land1",
                    status = "PENDING",
                    documentHash = null,
                    createdAt = System.currentTimeMillis(),
                    verifiedBy = null,
                    verifiedAt = null
                )
            )

            if (result.isSuccess) {
                Log.d("TEST_FIREBASE", "SUCCESS: ${result.getOrNull()}")
            } else {
                Log.e("TEST_FIREBASE", "FAILED: ${result.exceptionOrNull()}")
            }
        }
    }

    fun loadPendingClaims() {
        viewModelScope.launch {

            val result = container.getPendingClaims()

            when (result) {

                is DomainResult.Success -> {

                    val mapped = result.data.map { claim ->

                        val user = container.getUserById(claim.claimantId)

                        ClaimWithUser(
                            id = claim.id,
                            parcelId = claim.parcelId,
                            claimantName = user?.name ?: "Unknown",
                            status = claim.status,
                            documentPath = claim.documentPath
                        )
                    }

                    pendingClaims = mapped
                }

                is DomainResult.Failure -> {
                    status = result.error.toMessage()
                }
            }
        }
    }
}