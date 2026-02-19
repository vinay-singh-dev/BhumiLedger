package com.example.bhumiledger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.bhumiledger.MainViewModel
import com.example.bhumiledger.auth.AuthViewModel
import com.example.bhumiledger.ui.CitizenClaimScreen
import com.example.bhumiledger.ui.AuthorityVerificationScreen
import com.example.bhumiledger.auth.LoginScreen
import com.example.bhumiledger.auth.RegisterScreen

@Composable
fun BhumiLedgerNavGraph(
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }


        composable("citizen") {
            CitizenClaimScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("authority") {
            AuthorityVerificationScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}
