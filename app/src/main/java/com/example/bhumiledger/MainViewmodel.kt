package com.example.bhumiledger

import BhumiLedgerContainer
import android.util.Log
import androidx.lifecycle.ViewModel
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

        Log.d("ROOM_TEST", "===== FULL FLOW TEST START =====")

        val parcelId = "parcel-test-001"
        val claimantId = "Vinay"

        val submitResult = container.submitOwnershipClaim(parcelId, claimantId)

        if (submitResult is DomainResult.Success) {

            val claim = submitResult.data

            Log.d("ROOM_TEST", "Claim created: ${claim.id}")

            val verifyResult = container.verifyOwnershipClaim(claim.id)

            if (verifyResult is DomainResult.Success) {

                val verifiedClaim = verifyResult.data

                Log.d("ROOM_TEST", "Claim verified: ${verifiedClaim.id}")

                val registryResult = container.createRegistryEntry(verifiedClaim)

                if (registryResult is DomainResult.Success) {

                    Log.d("ROOM_TEST", "Registry entry created")

                    val historyResult =
                        container.getOwnershipHistory(parcelId)

                    if (historyResult is DomainResult.Success) {

                        Log.d(
                            "ROOM_TEST",
                            "Registry history size: ${historyResult.data.size}"
                        )

                        Log.d("ROOM_TEST", "===== FULL FLOW TEST END =====")
                    }
                }
            }
        }
    }
}