package com.example.bhumiledger.data.mapper


import com.example.bhumiledger.data.local.room.BlockEntity
import com.example.bhumiledger.domain.model.Block

fun BlockEntity.toDomain(): Block {
    return Block(
        index = index,
        previousHash = previousHash,
        dataHash = dataHash,
        blockHash = blockHash,
        timestamp = timestamp
    )
}

fun Block.toEntity(): BlockEntity {
    return BlockEntity(
        index = index,
        previousHash = previousHash,
        dataHash = dataHash,
        blockHash = blockHash,
        timestamp = timestamp
    )
}
