package com.example.bhumiledger.domain.model

data class OwnershipClaim(
    val id: String,
    val parcelId: String,
    val claimantId: String,
    val status: ClaimStatus
)

enum class ClaimStatus {
    PENDING,
    VERIFIED,
    REJECTED
}