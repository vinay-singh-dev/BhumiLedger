package com.example.bhumiledger

import androidx.lifecycle.ViewModel
import com.example.bhumiledger.data.BhumiLedgerContainer
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.result.DomainResult

class MainViewModel(
    private val container: BhumiLedgerContainer
) : ViewModel() {

    fun submitClaim(parcelId: String, claimantId: String): DomainResult<OwnershipClaim> {
        return container.submitOwnershipClaim(parcelId, claimantId)
    }

    fun verifyClaim(claimId: String): DomainResult<OwnershipClaim> {
        return container.verifyOwnershipClaim(claimId)
    }

    fun createRegistryEntry(claim: OwnershipClaim): DomainResult<RegistryEntry> {
        return container.createRegistryEntry(claim)
    }

    fun getOwnershipHistory(parcelId: String): DomainResult<List<RegistryEntry>> {
        return container.getOwnershipHistory(parcelId)
    }
}
