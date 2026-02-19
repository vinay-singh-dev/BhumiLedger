package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhumiledger.MainViewModel
import com.example.bhumiledger.auth.AuthViewModel

@Composable
fun CitizenClaimScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var parcelId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Logout Button (Top Right)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ElevatedButton(
                onClick = {
                    println("LOGOUT CLICKED")

                    authViewModel.logout()

                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }




                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Logout"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }
        }

        // Center Content
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Citizen Panel",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = parcelId,
                onValueChange = { parcelId = it },
                label = { Text("Parcel ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val userId = authViewModel.getCurrentUserId()

                    if (userId != null) {
                        mainViewModel.submitClaim(parcelId, userId)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Claim")
            }

            Text("Status: ${mainViewModel.status}")
        }
    }
}
