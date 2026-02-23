package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun AuthorityVerificationScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    // 🔥 Load pending claims when screen opens
    LaunchedEffect(Unit) {
        mainViewModel.loadPendingClaims()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Logout Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ElevatedButton(
                onClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
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

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Pending Claims",
                style = MaterialTheme.typography.headlineMedium
            )

            LazyColumn(
                modifier = Modifier.weight(1f, fill = false)
            ) {

                items(mainViewModel.pendingClaims) { claim ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text("Parcel: ${claim.parcelId}")
                            Text("Claimant: ${claim.claimantName}")
                            Text("Status: ${claim.status}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Button(
                                    onClick = {
                                        mainViewModel.verifyClaim(claim.id)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Verify")
                                }

                                Button(
                                    onClick = {
                                        mainViewModel.rejectClaim(claim.id)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Reject")
                                }
                            }
                        }
                    }
                }
            }

            // 👇 GLOBAL BLOCKCHAIN CONTROLS

            Button(
                onClick = { mainViewModel.validateChain() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Validate Blockchain")
            }

            Text("Chain Valid: ${mainViewModel.isChainValid}")

            Text("Status: ${mainViewModel.status}")

            Button(
                onClick = { navController.navigate("blockchain") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Blockchain")
            }
        }
    }
}