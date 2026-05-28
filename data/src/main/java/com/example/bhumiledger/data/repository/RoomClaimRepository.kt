package com.example.bhumiledger.data.repository

import android.util.Log
import android.util.Log.e
import com.example.bhumiledger.data.local.room.ClaimDao
import com.example.bhumiledger.data.mapper.ClaimMapper
import com.example.bhumiledger.data.mapper.toDto
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import com.example.bhumiledger.domain.repository.ClaimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.bhumiledger.data.remote.firestore.FirestoreDataSource
import com.example.bhumiledger.data.remote.storage.CloudinaryDataSource

class RoomClaimRepository(
    private val dao: ClaimDao,
    private val firestore: FirestoreDataSource,
    private val storage: CloudinaryDataSource
) : ClaimRepository {

    private val mapper = ClaimMapper()

    override suspend fun saveClaim(claim: OwnershipClaim) {

        // saved locally
        dao.insert(mapper.toEntity(claim))
    }

    override suspend fun getClaimById(claimId: String): OwnershipClaim? {
        return dao.getById(claimId)?.let {
            mapper.toDomain(it)
        }
    }

    override suspend fun getPendingClaimForParcel(parcelId: String): OwnershipClaim? {
        return dao.getPendingClaim(parcelId)?.let {
            mapper.toDomain(it)
        }
    }

    override suspend fun updateClaim(claim: OwnershipClaim) {
        val existing = dao.getEntityByClaimId(claim.id)
            ?: return

        val updated = existing.copy(
            status = claim.status.name,
            documentPath = claim.documentPath,
            documentUrl = claim.documentUrl,
            documentHash = claim.documentHash
        )

        dao.update(updated)
    }

    override suspend fun getAllPendingClaims(): List<OwnershipClaim> {
        return dao.getAllPending().map {
            mapper.toDomain(it)
        }
    }

    override fun getClaimsByUser(userId: String): Flow<List<OwnershipClaim>> {
        return dao.getByUserId(userId).map { list ->
            list.map { mapper.toDomain(it) }
        }
    }

    override suspend fun updateSyncState(
        claimId: String,
        state: SyncState
    ) {
        dao.updateSyncState(claimId, state)
    }

    override suspend fun getPendingSyncClaims(): List<OwnershipClaim> {
        return dao.getPendingSyncClaims().map {
            mapper.toDomain(it)
        }
    }


    override suspend fun pushToFirestore(

        claim: OwnershipClaim
    ): Result<String> {

        return try {

            Log.d(
                "SYNC_DEBUG",
                "Starting sync for claimId=${claim.id}"
            )

//            val dto = claim.toDto()
                var updatedClaim = claim

            // step 1 upload PDF if needed
            val documentPath = claim.documentPath

            if (documentPath != null && claim.documentUrl == null) {
                Log.d(
                    "SYNC_DEBUG",
                    "uploading pdf for claimId=${claim.id}")

                val uploadResult = storage.uploadPdf(
                    claim.id,
                    documentPath
                )

                if (uploadResult.isFailure) {
                dao.updateSyncState(
                    claim.id,
                    SyncState.FAILED

                )
                    Log.d("SYNC_DEBUG",
                        "PDF upload failed for claimId = ${claim.id} , error = ${uploadResult.exceptionOrNull()})"
                    )
                    return Result.failure(
                        uploadResult.exceptionOrNull()
                            ?: Exception("cloudinary upload failed")
                    )

            }
                val cloudUrl = uploadResult.getOrNull()

                Log.d("SYNC DEBUG",
                        "PDF upload success for claimId = ${claim.id} , url = $cloudUrl")

            // Step 2 save cloud url locally
            updatedClaim = claim.copy(
                documentUrl = cloudUrl
            )
                dao.update(
                mapper.toEntity(updatedClaim)
                )

                Log.d(
                    "SYNC_DEBUG",
                    "Local Room updated with documentUrl for claimId=${claim.id}"
                )

            }

            // step 3 push metadata to firestore

            val dto = updatedClaim.toDto()

            Log.d("SYNC_DEBUG",
                "Uploading Firestore DTO for claimId =${claim.id}")

            val firestoreResult = firestore.addClaim(dto)

            if(firestoreResult.isSuccess) {

                dao.updateSyncState(
                    claim.id,
                    SyncState.SYNCED
                )

                Log.d(
                    "SYNC_DEBUG",
                    "Firestore sync SUCCESS for claimId = ${claim.id}"
                )

            } else {
                dao.updateSyncState(
                    claim.id,
                    SyncState.FAILED
                )

                Log.e("SYNC_DEBUG",
                    "FireStore Sync failed for claimId = ${claim.id}, error = ${firestoreResult.exceptionOrNull()}")

            }

            firestoreResult

        } catch (e:Exception) {
            dao.updateSyncState (
                claim.id,
                SyncState.FAILED
            )

            Log.e(
                "SYNC_DEBUG",
                "Sync EXCEPTION for claimId = ${claim.id}, error = $e"
            )
            Result.failure(e)

            }

            }
}
