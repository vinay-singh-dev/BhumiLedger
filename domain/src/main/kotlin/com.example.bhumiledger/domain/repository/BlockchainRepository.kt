package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.Block

interface BlockchainRepository {
    suspend fun getLastBlock(): Block?
   suspend  fun saveBlock(block: Block)
    suspend fun getAllBlocks(): List<Block>
    suspend fun validateChain(): Boolean
}