package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.ClaimDao
import com.example.bhumiledger.data.mapper.ClaimMapper
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import com.example.bhumiledger.domain.repository.ClaimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomClaimRepository(
    private val dao: ClaimDao
) : ClaimRepository {

    private val mapper = ClaimMapper()

    override suspend fun saveClaim(claim: OwnershipClaim) {
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
}