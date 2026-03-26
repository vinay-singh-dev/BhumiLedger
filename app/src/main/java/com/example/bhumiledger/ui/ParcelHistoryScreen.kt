package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhumiledger.MainViewModel

private fun formatDate(time: Long): String {
    val formatter = java.text.SimpleDateFormat(
        "dd MMM yyyy (HH:mm)",
        java.util.Locale.getDefault()
    )
    return formatter.format(java.util.Date(time))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelHistoryScreen(
    parcelId: String,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    LaunchedEffect(parcelId) {
        mainViewModel.loadOwnershipHistory(parcelId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parcel Ownership History") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Parcel ID: $parcelId",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (mainViewModel.ownershipHistory.isEmpty()) {

                Text(
                    text = "No ownership history found.",
                    style = MaterialTheme.typography.bodyMedium
                )

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(mainViewModel.ownershipHistory) { entry ->

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
                                    text = "Owner",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Text(
                                    text = entry.ownerName,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Divider()

                                Text(
                                    text = "Verified By Authority",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Surface(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = entry.authorityName,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        )
                                    )
                                }

                                Divider()

                                Text(
                                    text = "Ownership Created",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Text(
                                    text = formatDate(entry.timestamp),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    text = "Verified On",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Text(
                                    text = formatDate(entry.verifiedAt),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}