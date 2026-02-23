package com.example.bhumiledger.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhumiledger.MainViewModel

@Composable
fun ParcelHistoryScreen(
    parcelId: String,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    LaunchedEffect(parcelId) {
        mainViewModel.loadOwnershipHistory(parcelId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Ownership History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(mainViewModel.ownershipHistory) { entry ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text("Owner: ${entry.ownerName}")
                        Text("Timestamp: ${entry.timestamp}")
                    }
                }
            }
        }
    }
}