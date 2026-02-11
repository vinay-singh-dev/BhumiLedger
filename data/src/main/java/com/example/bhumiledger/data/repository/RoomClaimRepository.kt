package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.ClaimDao
import com.example.bhumiledger.data.local.room.ClaimEntity
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import kotlinx.coroutines.runBlocking

class RoomClaimRepository(
    private val dao: ClaimDao
) : ClaimRepository {

    override fun saveClaim(claim: OwnershipClaim) {

        val entity = ClaimEntity(
            claimId = claim.id,
            parcelId = claim.parcelId,
            claimantId = claim.claimantId,
            status = claim.status.name,
            createdAt = System.currentTimeMillis()
        )

        runBlocking {
            dao.insert(entity)
        }
    }

    override fun getClaimById(claimId: String): OwnershipClaim? {

        return runBlocking {

            dao.getById(claimId)?.let {

                OwnershipClaim(
                    id = it.claimId,
                    parcelId = it.parcelId,
                    claimantId = it.claimantId,
                    status = ClaimStatus.valueOf(it.status)
                )
            }
        }
    }

    override fun getPendingClaimForParcel(parcelId: String): OwnershipClaim? {

        return runBlocking {

            dao.getPendingClaim(parcelId)?.let {

                OwnershipClaim(
                    id = it.claimId,
                    parcelId = it.parcelId,
                    claimantId = it.claimantId,
                    status = ClaimStatus.valueOf(it.status)
                )
            }
        }
    }

    override fun updateClaim(claim: OwnershipClaim) {

        val entity = ClaimEntity(
            claimId = claim.id,
            parcelId = claim.parcelId,
            claimantId = claim.claimantId,
            status = claim.status.name,
            createdAt = System.currentTimeMillis()
        )

        runBlocking {
            dao.update(entity)
        }
    }
}
