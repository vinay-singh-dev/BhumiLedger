package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelectionScreen(
    onCitizenSelected: () -> Unit,
    onAuthoritySelected: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("Select Role")

        Button(
            onClick = onCitizenSelected
        ) {
            Text("Citizen")
        }

        Button(
            onClick = onAuthoritySelected
        ) {
            Text("Authority")
        }
    }
}
