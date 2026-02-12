package com.example.bhumiledger

import BhumiLedgerContainer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.bhumiledger.theme.BhumiLedgerTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = BhumiLedgerContainer(applicationContext)
        viewModel = MainViewModel(container)

        enableEdgeToEdge()

        setContent {

            BhumiLedgerTheme {

                val container = BhumiLedgerContainer(applicationContext)

                val viewModel = remember {
                    MainViewModel(container)
                }

                ClaimScreen(viewModel)

            }

        }

    }
}
