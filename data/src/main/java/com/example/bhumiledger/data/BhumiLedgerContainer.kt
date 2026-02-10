package com.example.bhumiledger.data

import com.example.bhumiledger.data.repository.InMemoryClaimRepository
import com.example.bhumiledger.data.repository.InMemoryRegistryRepository
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.usecase.CreateRegistryEntry
import com.example.bhumiledger.domain.usecase.GetOwnershipHistory
import com.example.bhumiledger.domain.usecase.SubmitOwnershipClaim
import com.example.bhumiledger.domain.usecase.VerifyOwnershipClaim

class BhumiLedgerContainer {

    val claimRepository = InMemoryClaimRepository()
    val registryRepository = InMemoryRegistryRepository()
    val submitOwnershipClaim = SubmitOwnershipClaim(claimRepository)
    val verifyOwnershipClaim = VerifyOwnershipClaim(claimRepository)
    val createRegistryEntry = CreateRegistryEntry(registryRepository)
    val getOwnershipHistory = GetOwnershipHistory(registryRepository)
}