package com.example.bhumiledger.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocks")
data class BlockEntity(
    @PrimaryKey val index: Int,
    val previousHash: String,
    val dataHash: String,
    val blockHash: String,
    val timestamp: Long
)