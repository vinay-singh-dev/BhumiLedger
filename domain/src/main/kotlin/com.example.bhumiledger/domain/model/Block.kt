package com.example.bhumiledger.domain.model

data class Block(
    val index: Int,
    val previousHash: String,
    val dataHash: String,
    val blockHash: String,
    val timestamp: Long
)