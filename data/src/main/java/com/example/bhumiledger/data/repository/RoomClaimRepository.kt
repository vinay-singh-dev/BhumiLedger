package com.example.bhumiledger.data.repository

import android.util.Log
import com.example.bhumiledger.data.local.room.ClaimDao
import com.example.bhumiledger.data.local.room.ClaimEntity
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import kotlinx.coroutines.runBlocking

class RoomClaimRepository(
    private val dao: ClaimDao
) : ClaimRepository {

    override suspend fun saveClaim(claim: OwnershipClaim) {

        val entity = ClaimEntity(
            claimId = claim.id,
            parcelId = claim.parcelId,
            claimantId = claim.claimantId,
            status = claim.status.name,
            createdAt = System.currentTimeMillis()
        )

        dao.insert(entity)
    }

    override suspend fun getClaimById(claimId: String): OwnershipClaim? {

        val entity = dao.getById(claimId)

        return entity?.let {
            OwnershipClaim(
                id = it.claimId,
                parcelId = it.parcelId,
                claimantId = it.claimantId,
                status = ClaimStatus.valueOf(it.status)
            )
        }
    }

    override suspend fun getPendingClaimForParcel(parcelId: String): OwnershipClaim? {

        val entity = dao.getPendingClaim(parcelId)

        return entity?.let {
            OwnershipClaim(
                id = it.claimId,
                parcelId = it.parcelId,
                claimantId = it.claimantId,
                status = ClaimStatus.valueOf(it.status)
            )
        }
    }

    override suspend fun updateClaim(claim: OwnershipClaim) {

        val existing = dao.getEntityByClaimId(claim.id)
            ?: return

        val updated = existing.copy(
            status = claim.status.name
        )

        dao.update(updated)
    }

    override suspend fun getAllPendingClaims(): List<OwnershipClaim> {
        return dao.getAllPending().map {
            OwnershipClaim(
                id = it.claimId,
                parcelId = it.parcelId,
                claimantId = it.claimantId,
                status = ClaimStatus.valueOf(it.status)
            )
        }
    }

    override suspend fun getClaimsByUser(userId: String): List<OwnershipClaim> {
        return dao.getByUserId(userId).map {
            OwnershipClaim(
                id = it.claimId,
                parcelId = it.parcelId,
                claimantId = it.claimantId,
                status = ClaimStatus.valueOf(it.status)
            )
        }
    }
}