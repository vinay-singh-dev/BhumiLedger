package com.example.bhumiledger

import BhumiLedgerContainer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.bhumiledger.theme.BhumiLedgerTheme
import com.example.bhumiledger.ui.AuthorityVerificationScreen
import com.example.bhumiledger.ui.CitizenClaimScreen
import com.example.bhumiledger.ui.RoleSelectionScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container =
            BhumiLedgerContainer(applicationContext)

        val viewModel =
            MainViewModel(container)

        enableEdgeToEdge()

        setContent {

            BhumiLedgerTheme {

                var screen by remember {
                    mutableStateOf("role")
                }

                when(screen) {

                    "role" -> RoleSelectionScreen(

                        onCitizenSelected = {
                            screen = "citizen"
                        },

                        onAuthoritySelected = {
                            screen = "authority"
                        }
                    )

                    "citizen" ->
                        CitizenClaimScreen(viewModel)

                    "authority" ->
                        AuthorityVerificationScreen(viewModel)
                }
            }
        }
    }
}
