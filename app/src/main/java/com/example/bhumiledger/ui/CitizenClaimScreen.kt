package com.example.bhumiledger.ui
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.SyncState
import utils.FileUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenClaimScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val context = LocalContext.current
    val state by authViewModel.authState.collectAsState()

    var selectedDocumentPath by remember { mutableStateOf<String?>(null) }
    var parcelId by remember { mutableStateOf("") }

    val documentLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            uri?.let {
                val file = FileUtils.copyPdfToAppStorage(context, it)
                selectedDocumentPath = file.absolutePath
            }
        }

    LaunchedEffect(authViewModel.getCurrentUserId()) {
        authViewModel.getCurrentUserId()?.let {
            mainViewModel.observeUserClaims(it)
        }
    }

    LaunchedEffect(state is AuthState.LoggedOut) {
        if(state is AuthState.LoggedOut) {
            navController.navigate("login") {
                popUpTo(0){inclusive = true}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Citizen Dashboard") },
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

            // -------------------------
            // SUBMIT CLAIM SECTION
            // -------------------------

            Text(
                text = "Submit Ownership Claim",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = parcelId,
                onValueChange = { parcelId = it },
                label = { Text("Parcel ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    documentLauncher.launch(arrayOf("application/pdf"))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (selectedDocumentPath == null)
                        "Upload Land Document (PDF)"
                    else
                        "Document Selected ✓"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val userId = authViewModel.getCurrentUserId()
                    if (userId != null) {
                        mainViewModel.submitClaim(
                            parcelId,
                            userId,
                            selectedDocumentPath,
                        )

                        // 🔥 Smart WorkRequest with constraint


                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Claim")
            }

//            Button(
//                onClick = {
//                    mainViewModel.testFirestoreDirect()
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Test Firestore Direct")
//            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = mainViewModel.status,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // -------------------------
            // CLAIM LIST SECTION
            // -------------------------

            Text(
                text = "My Claims",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(mainViewModel.userClaims) { claim ->

                    val statusColor = when (claim.status) {
                        ClaimStatus.PENDING -> MaterialTheme.colorScheme.tertiary
                        ClaimStatus.VERIFIED -> MaterialTheme.colorScheme.primary
                        ClaimStatus.REJECTED -> MaterialTheme.colorScheme.error
                    }

                    val syncColor = when (claim.syncState) {
                        SyncState.PENDING -> MaterialTheme.colorScheme.tertiary
                        SyncState.SYNCED -> MaterialTheme.colorScheme.primary
                        SyncState.FAILED -> MaterialTheme.colorScheme.error
                    }

                    val syncText = when (claim.syncState) {
                        SyncState.PENDING -> "Syncing..."
                        SyncState.SYNCED -> "Synced"
                        SyncState.FAILED -> "Sync Failed"

                    }

                    Card(
                        shape = MaterialTheme.shapes.large,
                        elevation = CardDefaults.cardElevation(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {

                            Text(
                                text = "Parcel ID",
                                style = MaterialTheme.typography.labelMedium
                            )

                            Text(
                                text = claim.parcelId,
                                style = MaterialTheme.typography.titleMedium
                            )

                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                DividerDefaults.color
                            )

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = "Status",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Surface(
                                    color = statusColor.copy(alpha = 0.15f),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = claim.status.name,
                                        color = statusColor,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        ),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = "Sync",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Surface(
                                    color = syncColor.copy(alpha = 0.15f),
                                    shape = MaterialTheme.shapes.small
                                ) {
//
                                    Text(
                                        text =  syncText,
                                        color = syncColor,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        ),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }

                            when (claim.status) {
                                ClaimStatus.PENDING -> {
                                    Text(
                                        text = "Waiting for authority approval",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                ClaimStatus.VERIFIED -> {
                                    Text(
                                        text = "Ownership successfully granted",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                ClaimStatus.REJECTED -> {
                                    Text(
                                        text = "Claim rejected by authority",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
