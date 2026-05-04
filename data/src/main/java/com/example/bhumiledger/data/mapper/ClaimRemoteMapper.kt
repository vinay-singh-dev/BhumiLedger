package com.example.bhumiledger.data.mapper

import com.example.bhumiledger.data.remote.firestore.dto.ClaimDto
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.SyncState
import kotlin.String

    fun OwnershipClaim.toDto(): ClaimDto {
        return ClaimDto(
            claimId = id,
            owner = claimantId,
            land = parcelId,
            status = status.name,
            documentHash = documentPath, // temp, later real hash
            createdAt = createdAt,
            verifiedBy = null, // fill when verified
            verifiedAt = null
        )

    }

    fun ClaimDto.toDomain(): OwnershipClaim {
        return OwnershipClaim(
            id = claimId,
            parcelId = land,
            claimantId = owner,
            status = ClaimStatus.valueOf(status),
            documentPath = documentHash,
            syncState = SyncState.SYNCED,
            createdAt = createdAt
        )
    }

