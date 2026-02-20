package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.Block
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.repository.BlockchainRepository
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import utils.sha256

class VerifyOwnershipClaim(
    private val claimRepository: ClaimRepository,
    private val registryRepository: RegistryRepository,
    private val blockchainRepository: BlockchainRepository
) {

    suspend operator fun invoke(
        claimId: String,
        role: UserRole
    ): DomainResult<OwnershipClaim> {

        // CRITICAL: Only authority can verify
        if (role != UserRole.AUTHORITY) {
            return DomainResult.Failure(
                DomainError.UnauthorizedAccess
            )
        }

        if (claimId.isBlank()) {
            return DomainResult.Failure(DomainError.InvalidInput)
        }

        val claim =
            claimRepository.getClaimById(claimId)
                ?: return DomainResult.Failure(
                    DomainError.ClaimNotFound
                )

        if (claim.status != ClaimStatus.PENDING) {
            return DomainResult.Failure(
                DomainError.InvalidClaimState
            )
        }

        // Mark verified
        val verifiedClaim =
            claim.copy(
                status = ClaimStatus.VERIFIED
            )

        claimRepository.updateClaim(
            verifiedClaim
        )

        val now = System.currentTimeMillis()

        // Create registry entry automatically
        registryRepository.save(
            RegistryEntry(
                parcelId = verifiedClaim.parcelId,
                ownerId = verifiedClaim.claimantId,
                createdAt = now
            )
        )

        val previousBlock = blockchainRepository.getLastBlock()
        val previousHash = previousBlock?.blockHash ?: "GENESIS"

        val dataString =
            verifiedClaim.id +
                    verifiedClaim.parcelId +
                    verifiedClaim.claimantId +
                    now

        val dataHash = sha256(dataString)

        val blockHash = sha256(previousHash + dataHash)

        val block = Block(
            index = (previousBlock?.index ?: 0) + 1,
            previousHash = previousHash,
            dataHash = dataHash,
            blockHash = blockHash,
            timestamp = now
        )

        blockchainRepository.saveBlock(block)

        val blocks = blockchainRepository.getAllBlocks()

        println("===== FULL BLOCKCHAIN =====")
        blocks.forEach {
            println("Index: ${it.index}")
            println("Previous: ${it.previousHash}")
            println("Hash: ${it.blockHash}")
            println("------")
        }
        println("===========================")

        return DomainResult.Success(
            verifiedClaim
        )
    }
}
