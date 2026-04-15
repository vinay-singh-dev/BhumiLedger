package com.example.bhumiledger.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhumiledger.MainViewModel
import com.example.bhumiledger.auth.AuthState
import com.example.bhumiledger.auth.AuthViewModel
import utils.openPdf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorityVerificationScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val context = LocalContext.current
    val state by authViewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.loadPendingClaims()
    }

    LaunchedEffect(state is AuthState.LoggedOut) {
        if (state is AuthState.LoggedOut) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Authority Dashboard") },
                actions = {
                    TextButton(
                        onClick = {
                            authViewModel.logout()

                        }
                    ) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {

            // ----------------------------
            // Pending Claims Section
            // ----------------------------

            Text(
                text = "Pending Claims",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {

                items(mainViewModel.pendingClaims) { claim ->

                    Card(
                        shape = MaterialTheme.shapes.large,
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                text = "Parcel ID",
                                style = MaterialTheme.typography.labelMedium
                            )

                            Text(
                                text = claim.parcelId,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        "parcel_history/${claim.parcelId}"
                                    )
                                }
                            )

                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                DividerDefaults.color
                            )

                            Text(
                                text = "Claimant: ${claim.claimantName}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // ACTIONS

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                OutlinedButton(
                                    onClick = {
                                        claim.documentPath?.let {
                                            openPdf(context, it)
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = claim.documentPath != null
                                ) {
                                    Text(
                                        if (claim.documentPath != null)
                                            "View Uploaded Document"
                                        else
                                            "No Document Uploaded"
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Button(
                                        onClick = {
                                            mainViewModel.verifyClaim(claim.id)
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Approve")
                                    }

                                    OutlinedButton(
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
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ----------------------------
            // Blockchain Integrity Section
            // ----------------------------

            Text(
                text = "Blockchain Integrity",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            val chainColor =
                if (mainViewModel.isChainValid)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error

            Surface(
                color = chainColor.copy(alpha = 0.15f),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text =
                        if (mainViewModel.isChainValid)
                            "Blockchain Verified and Secure"
                        else
                            "Warning: Blockchain Integrity Compromised",
                    color = chainColor,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { mainViewModel.validateChain() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Revalidate Blockchain")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = mainViewModel.status,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate("blockchain")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Full Blockchain")
            }
        }
    }
}