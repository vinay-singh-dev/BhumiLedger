package com.example.bhumiledger.data.remote.firestore.dto

data class ClaimDto(
    val claimId: String,
    val owner: String,
    val land: String,
    val status: String,
    val documentHash: String?,
//    val documentUrl: String?,
    val createdAt: Long,
    val verifiedBy: String?,
    val verifiedAt: Long?
)