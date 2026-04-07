package com.example.bhumiledger.ui.model

data class RegistryEntryWithUser(
    val ownerName: String,
    val authorityName: String,
    val timestamp: Long,
    val verifiedAt: Long
)