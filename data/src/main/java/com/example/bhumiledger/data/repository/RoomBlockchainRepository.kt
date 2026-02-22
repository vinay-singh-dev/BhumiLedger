package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.BlockDao
import com.example.bhumiledger.data.mapper.toDomain
import com.example.bhumiledger.data.mapper.toEntity
import com.example.bhumiledger.domain.model.Block
import com.example.bhumiledger.domain.repository.BlockchainRepository

class RoomBlockchainRepository(
    private val blockDao: BlockDao
) : BlockchainRepository {

    override suspend fun getLastBlock(): Block? {
        return blockDao.getLast()?.toDomain()
    }

    override suspend fun saveBlock(block: Block) {
        blockDao.insert(block.toEntity())
    }

    override suspend fun getAllBlocks(): List<Block> {
        return blockDao.getAll().map { it.toDomain() }
    }
}