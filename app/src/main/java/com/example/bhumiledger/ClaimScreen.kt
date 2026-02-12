package com.example.bhumiledger

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClaimScreen(viewModel: MainViewModel) {

    var parcelId by remember { mutableStateOf("") }
    var claimantId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TextField(
            value = parcelId,
            onValueChange = { parcelId = it },
            label = { Text("Parcel ID") }
        )

        TextField(
            value = claimantId,
            onValueChange = { claimantId = it },
            label = { Text("Claimant ID") }
        )

        Button(
            onClick = {
                viewModel.submitClaim(parcelId, claimantId)
            }
        ) {
            Text("Submit Claim")
        }

        Button(
            onClick = {
                viewModel.verifyClaim()
            }
        ) {
            Text("Verify Claim")
        }

        Button(
            onClick = {
                viewModel.loadHistory(parcelId)
            }
        ) {
            Text("Load History")
        }

        Text("Status: ${viewModel.status}")

        Text("Last Claim ID: ${viewModel.lastClaimId}")

        Text("History Size: ${viewModel.historySize}")
    }
}
