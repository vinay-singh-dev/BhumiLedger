package com.example.bhumiledger.data.repository

import android.util.Log
import com.example.bhumiledger.data.local.room.ClaimDao
import com.example.bhumiledger.data.mapper.ClaimMapper
import com.example.bhumiledger.data.mapper.toDto
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import com.example.bhumiledger.domain.repository.ClaimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.bhumiledger.data.remote.firestore.FirestoreDataSource

class RoomClaimRepository(
    private val dao: ClaimDao,
    private val firestore: FirestoreDataSource
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



    override suspend fun pushToFirestore(claim: OwnershipClaim): Result<String> {

        val dto = claim.toDto()
        Log.d("SYNC_DEBUG", "Uploading DTO = $dto")
            val result = firestore.addClaim(dto)
        return if (result.isSuccess) {
            Log.d("SYNC_DEBUG", "SUCCESS: ${result.getOrNull()}")
            dao.updateSyncState(claim.id,SyncState.SYNCED)
            result
        } else {
            dao.updateSyncState(claim.id,SyncState.FAILED)
            Log.e("SYNC_DEBUG", "FAILED: ${result.exceptionOrNull()}")
            result
        }

    }
}