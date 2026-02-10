package com.example.bhumiledger

import android.util.Log
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

    fun testOwnershipFlow() {
        val parcelId = "parcel-26112006"
        val claimantId = "Aneesh"

        val submitResult = container.submitOwnershipClaim(parcelId, claimantId)

        if (submitResult is DomainResult.Success) {

            val claim = submitResult.data

            val verifyResult = container.verifyOwnershipClaim(claim.id)

            if (verifyResult is DomainResult.Success) {
                val verifiedClaim = verifyResult.data

                val registryResult = container.createRegistryEntry(verifiedClaim)
                if (registryResult is DomainResult.Success) {

                    val historyResult = container.getOwnershipHistory(parcelId)
                    if (historyResult is DomainResult.Success) {
                        val history = historyResult.data

                        Log.d("BhumiLedger", "Ownership history size: ${history.size}")
                        Log.d("BhumiLedger", "Current Owner: ${history.last().ownerId}")

                    }
                }

            }
        }

    }
}
