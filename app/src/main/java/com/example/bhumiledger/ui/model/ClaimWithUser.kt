package com.example.bhumiledger.ui.model

import com.example.bhumiledger.domain.model.ClaimStatus

data class ClaimWithUser(
    val id: String,
    val parcelId: String,
    val claimantName: String,
    val status: ClaimStatus
)