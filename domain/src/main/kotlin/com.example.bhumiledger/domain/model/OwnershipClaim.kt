package com.example.bhumiledger.domain.model

data class OwnershipClaim(
    val id: String,
    val parcelId: String,
    val claimantId: String,
    val status: ClaimStatus,
    val documentPath: String? = null
)

enum class ClaimStatus {
    PENDING,
    VERIFIED,
    REJECTED
}