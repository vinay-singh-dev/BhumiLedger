package com.example.bhumiledger

import BhumiLedgerContainer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.result.DomainResult

class MainViewModel(
    private val container: BhumiLedgerContainer
) : ViewModel() {

    // UI observable state
    var status by mutableStateOf("Idle")
        private set

    var lastClaimId by mutableStateOf("")
        private set

    var historySize by mutableStateOf(0)
        private set


    // UI will call this
    fun submitClaim(parcelId: String, claimantId: String) {

        Log.d("ROOM_TEST", "UI submitClaim called")

        val result = container.submitOwnershipClaim(parcelId, claimantId)

        when (result) {

            is DomainResult.Success -> {

                val claim = result.data

                lastClaimId = claim.id

                status = "Claim Created: ${claim.id}"

                Log.d("ROOM_TEST", status)
            }

            is DomainResult.Failure -> {

                status = "Claim Failed"

                Log.e("ROOM_TEST", status)
            }
        }
    }


    fun verifyClaim() {

        if (lastClaimId.isEmpty()) {
            status = "No claim to verify"
            return
        }

        val result = container.verifyOwnershipClaim(lastClaimId)

        when (result) {

            is DomainResult.Success -> {

                status = "Claim Verified"

                Log.d("ROOM_TEST", status)
            }

            is DomainResult.Failure -> {

                status = "Verification Failed"

                Log.e("ROOM_TEST", status)
            }
        }
    }


    fun loadHistory(parcelId: String) {

        val result = container.getOwnershipHistory(parcelId)

        if (result is DomainResult.Success) {

            historySize = result.data.size

            status = "History loaded: $historySize entries"

            Log.d("ROOM_TEST", status)
        }
    }


    // KEEP this only for debug, not production
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
