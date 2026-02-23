package com.example.bhumiledger.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.*
import com.example.bhumiledger.MainViewModel
import com.example.bhumiledger.auth.AuthViewModel
import com.example.bhumiledger.ui.CitizenClaimScreen
import com.example.bhumiledger.ui.AuthorityVerificationScreen
import com.example.bhumiledger.auth.LoginScreen
import com.example.bhumiledger.auth.RegisterScreen
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.ui.BlockchainViewerScreen

@Composable
fun BhumiLedgerNavGraph(
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel
) {

    val navController = rememberNavController()
    val role = authViewModel.getCurrentUserRole()

    LaunchedEffect(role) {
        if (role != null) {
            when (role) {
                UserRole.CITIZEN -> navController.navigate("citizen") {
                    popUpTo("login") { inclusive = true }
                }
                UserRole.AUTHORITY -> navController.navigate("authority") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

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

        composable("blockchain") {
            BlockchainViewerScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}
