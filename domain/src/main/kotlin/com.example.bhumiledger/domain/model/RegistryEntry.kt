package com.example.bhumiledger.domain.model

data class RegistryEntry(
    val parcelId: String,
    val ownerId: String,
    val createdAt: Long
)