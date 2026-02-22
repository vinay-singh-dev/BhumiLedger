package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.repository.BlockchainRepository
import utils.sha256

class ValidateBlockchainUseCase(
    private val blockchainRepository: BlockchainRepository
) {

    suspend operator fun invoke(): Boolean {

        val blocks = blockchainRepository.getAllBlocks()

        if (blocks.isEmpty()) return true

        for (i in 1 until blocks.size) {

            val previous = blocks[i - 1]
            val current = blocks[i]

            // Check previous hash linkage
            if (current.previousHash != previous.blockHash) {
                return false
            }

            // Recalculate hash
            val recalculatedHash =
                sha256(current.previousHash + current.dataHash)

            if (recalculatedHash != current.blockHash) {
                return false
            }
        }

        return true
    }
}