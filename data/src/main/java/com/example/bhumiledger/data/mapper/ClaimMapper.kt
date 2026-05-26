package com.example.bhumiledger.data.mapper

import com.example.bhumiledger.data.local.room.ClaimEntity
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim

class ClaimMapper {

    fun toDomain( entity : ClaimEntity): OwnershipClaim {
        return OwnershipClaim(
            id = entity.claimId,
            parcelId = entity.parcelId,
            claimantId = entity.claimantId,
            status = ClaimStatus.valueOf(entity.status),
            documentHash = entity.documentHash,
            documentPath = entity.documentPath,
//            documentUrl = entity.documentUrl,
            syncState = entity.syncState,
            createdAt = entity.createdAt
        )
    }

    fun toEntity( domain : OwnershipClaim): ClaimEntity {
        return ClaimEntity(
            claimId = domain.id,
            parcelId = domain.parcelId,
            claimantId = domain.claimantId,
            status = domain.status.name,
            createdAt = domain.createdAt,
            documentHash = domain.documentHash,
            documentPath = domain.documentPath,
//            documentUrl = domain.documentUrl,
            syncState = domain.syncState
        )
    }


}