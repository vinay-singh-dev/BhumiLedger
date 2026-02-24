package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.BlockDao
import com.example.bhumiledger.data.mapper.toDomain
import com.example.bhumiledger.data.mapper.toEntity
import com.example.bhumiledger.domain.model.Block
import com.example.bhumiledger.domain.repository.BlockchainRepository
import utils.sha256

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

    override suspend fun validateChain(): Boolean {

        val blocks = blockDao.getAll()
            .sortedBy { it.index }
            .map { it.toDomain() }

        if (blocks.isEmpty()) return true

        for (i in 1 until blocks.size) {

            val current = blocks[i]
            val previous = blocks[i - 1]

            // Check previous hash linkage
            if (current.previousHash != previous.blockHash) {
                return false
            }

            // Recalculate hash
            val recalculatedHash =
                sha256(current.previousHash + current.dataHash)

            if (current.blockHash != recalculatedHash) {
                return false
            }
        }

        return true
    }
    }
