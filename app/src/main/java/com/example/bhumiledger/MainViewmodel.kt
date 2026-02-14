package com.example.bhumiledger

import BhumiLedgerContainer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.result.DomainResult

class MainViewModel(
    private val container: BhumiLedgerContainer
) : ViewModel() {

    var status by mutableStateOf("Idle")
        private set

    var lastClaimId by mutableStateOf("")
        private set

    var historySize by mutableStateOf(0)
        private set


    // CITIZEN ACTION
    fun submitClaim(parcelId: String, claimantId: String) {

        Log.d("ROOM_TEST", "Citizen submitting claim")

        val result =
            container.submitOwnershipClaim(
                parcelId,
                claimantId
            )

        when(result) {

            is DomainResult.Success -> {

                val claim = result.data

                lastClaimId = claim.id

                status =
                    "Claim Submitted: ${claim.id}"

                Log.d("ROOM_TEST", status)
            }

            is DomainResult.Failure -> {

                status = result.error.toString()

                Log.e("ROOM_TEST", status)
            }
        }
    }


    // AUTHORITY ACTION
    fun verifyClaim(claimId: String) {

        Log.d("ROOM_TEST", "Authority verifying claim")

        val result =
            container.verifyOwnershipClaim(
                claimId,
                UserRole.AUTHORITY
            )

        when(result) {

            is DomainResult.Success -> {

                status = "Claim VERIFIED"

                Log.d("ROOM_TEST", status)
            }

            is DomainResult.Failure -> {

                status = result.error.toString()

                Log.e("ROOM_TEST", status)
            }
        }
    }


    private fun createRegistryEntry(
        claim: OwnershipClaim
    ) {

        Log.d(
            "ROOM_TEST",
            "Creating registry entry"
        )

        val registryResult =
            container.createRegistryEntry(
                claim
            )

        when(registryResult) {

            is DomainResult.Success -> {

                status =
                    "Ownership TRANSFERRED"

                Log.d("ROOM_TEST", status)
            }

            is DomainResult.Failure -> {

                status =
                    registryResult.error.toString()

                Log.e("ROOM_TEST", status)
            }
        }
    }


    fun loadHistory(parcelId: String) {

        Log.d("ROOM_TEST", "Loading history")

        val result =
            container.getOwnershipHistory(
                parcelId
            )

        when(result) {

            is DomainResult.Success -> {

                historySize =
                    result.data.size

                status =
                    "History entries: $historySize"

                Log.d("ROOM_TEST", status)
            }

            is DomainResult.Failure -> {

                status =
                    result.error.toString()

                Log.e("ROOM_TEST", status)
            }
        }
    }
}
