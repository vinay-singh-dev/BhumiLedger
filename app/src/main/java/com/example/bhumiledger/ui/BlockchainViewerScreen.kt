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
import com.example.bhumiledger.auth.AuthViewModel

@Composable
fun BlockchainViewerScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    LaunchedEffect(Unit) {
        mainViewModel.loadBlockchain()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Blockchain Ledger",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(mainViewModel.blockchain) { block ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text("Index: ${block.index}")

                        if (block.index == 1) {
                            Text("GENESIS BLOCK")
                        }

                        Text("Previous Hash:")
                        Text(block.previousHash)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("Block Hash:")
                        Text(block.blockHash)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("Data Hash:")
                        Text(block.dataHash)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("Timestamp: ${block.timestamp}")
                    }
                }
            }
        }
    }
}