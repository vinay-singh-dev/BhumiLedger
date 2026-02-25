package com.example.bhumiledger.ui
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhumiledger.MainViewModel
import com.example.bhumiledger.auth.AuthViewModel
import com.example.bhumiledger.domain.model.ClaimStatus
import utils.FileUtils

@Composable
fun CitizenClaimScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val context = LocalContext.current

    var selectedDocumentPath by remember {
        mutableStateOf<String?>(null)
    }

    val documentLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->

            uri?.let {
                val file = FileUtils.copyPdfToAppStorage(
                    context,
                    it
                )

                selectedDocumentPath = file.absolutePath
            }
        }

    var parcelId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.getCurrentUserId()?.let {
            mainViewModel.loadUserClaims(it)
        }
    }

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
                    documentLauncher.launch(
                        arrayOf("application/pdf")
                    )
                }
            ) {
                Text("Upload Land Document (PDF)")
            }

            Button(
                onClick = {
                    val userId = authViewModel.getCurrentUserId()

                    if (userId != null) {
                        mainViewModel.submitClaim(parcelId, userId,selectedDocumentPath)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Claim")
            }

            Text("Status: ${mainViewModel.status}")

            Text(
                text = "My Claims",
                style = MaterialTheme.typography.headlineSmall
            )

            LazyColumn {
                items(mainViewModel.userClaims) { claim ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text("Parcel: ${claim.parcelId}")
                            Text("Status: ${claim.status}")

                            when (claim.status) {
                                ClaimStatus.PENDING -> {
                                    Text("Waiting for authority approval")
                                }
                                ClaimStatus.VERIFIED -> {
                                    Text("Ownership granted")
                                }
                                ClaimStatus.REJECTED -> {
                                    Text("Claim rejected")
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
