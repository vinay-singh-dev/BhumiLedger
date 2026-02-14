package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bhumiledger.MainViewModel

@Composable
fun AuthorityVerificationScreen(viewModel: MainViewModel) {

    var claimId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Authority Panel")

        TextField(
            value = claimId,
            onValueChange = { claimId = it },
            label = { Text("Claim ID") }
        )

        Button(
            onClick = {
                viewModel.verifyClaim(claimId)
            }
        ) {
            Text("Verify Claim")
        }

        Text("Status: ${viewModel.status}")
    }
}
