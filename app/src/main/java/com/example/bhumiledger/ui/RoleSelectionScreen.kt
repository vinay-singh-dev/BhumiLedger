package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
    onCitizenSelected: () -> Unit,
    onAuthoritySelected: () -> Unit
) {

    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Card(
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {

                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "BhumiLedger",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Select your role to continue",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onCitizenSelected,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Continue as Citizen")
                    }

                    OutlinedButton(
                        onClick = onAuthoritySelected,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Continue as Authority")
                    }
                }
            }
        }
    }
}