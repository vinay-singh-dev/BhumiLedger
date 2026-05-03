package com.example.bhumiledger.data.repository

import android.util.Log
import com.example.bhumiledger.data.local.room.ClaimDao
import com.example.bhumiledger.data.mapper.ClaimMapper
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import com.example.bhumiledger.domain.repository.ClaimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import remote.firestore.FirestoreDataSource

class RoomClaimRepository(
    private val dao: ClaimDao,
    private val firestore: FirestoreDataSource
) : ClaimRepository {

    private val mapper = ClaimMapper()

    override suspend fun saveClaim(claim: OwnershipClaim) {

        // saved locally
        dao.insert(mapper.toEntity(claim))

        // sending to firestore and testing and checking
        val data = mapOf(
            "owner" to claim.claimantId,
            "land" to claim.parcelId,
            "timeStamp" to claim.createdAt
        )

        val result = firestore.addClaim(data)

        if (result.isSuccess) {
            Log.d("Firestore","Uploaded: ${result.getOrNull()}")
        } else {
            Log.d("FireStore","Upload Failed",result.exceptionOrNull())
        }

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
            documentPath = claim.documentPath
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
           list.map {mapper.toDomain(it) }
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
}