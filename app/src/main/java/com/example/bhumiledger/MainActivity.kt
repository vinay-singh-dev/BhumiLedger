package com.example.bhumiledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bhumiledger.auth.AuthViewModel
import com.example.bhumiledger.navigation.BhumiLedgerNavGraph
import com.example.bhumiledger.theme.BhumiLedgerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as BhumiLedgerApp
        val container = BhumiLedgerContainer(applicationContext)

        val mainViewModel = MainViewModel(container,
            app.syncScheduler)

        val authViewModel = AuthViewModel(
            loginUserUseCase = container.loginUserUseCase,
            registerUserUseCase = container.registerUserUseCase,
            sessionManager = container.sessionManager
        )

        enableEdgeToEdge()

        setContent {
            BhumiLedgerTheme {
                BhumiLedgerNavGraph(
                    mainViewModel = mainViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
