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

    override fun saveClaim(claim: OwnershipClaim) {

        Log.d("ROOM_TEST", "saveClaim called with: $claim")

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

        Log.d("ROOM_TEST", "saveClaim SUCCESS")
    }

    override fun getClaimById(claimId: String): OwnershipClaim? {

        Log.d("ROOM_TEST", "getClaimById called: $claimId")

        return runBlocking {

            val entity = dao.getById(claimId)

            Log.d("ROOM_TEST", "getClaimById result: $entity")

            entity?.let {
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

        Log.d("ROOM_TEST", "getPendingClaimForParcel called: $parcelId")

        return runBlocking {

            val entity = dao.getPendingClaim(parcelId)

            Log.d("ROOM_TEST", "getPendingClaimForParcel result: $entity")

            entity?.let {
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

        runBlocking {

            val existing = dao.getEntityByClaimId(claim.id)
                ?: run {
                    Log.e("ROOM_TEST", "Update failed. Claim not found: ${claim.id}")
                    return@runBlocking
                }

            val updated = existing.copy(
                status = claim.status.name
            )

            dao.update(updated)

            Log.d("ROOM_TEST", "Claim updated successfully: ${claim.id}")
        }
    }
}
