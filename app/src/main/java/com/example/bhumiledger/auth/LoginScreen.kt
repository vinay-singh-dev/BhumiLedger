package com.example.bhumiledger.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhumiledger.domain.model.UserRole

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val state by authViewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 🔹 Auto redirect if already logged in
    LaunchedEffect(Unit) {
        val role = authViewModel.getCurrentUserRole()
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

    // 🔹 Redirect after successful login
    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            val role = authViewModel.getCurrentUserRole()
            when (role) {
                UserRole.CITIZEN -> navController.navigate("citizen") {
                    popUpTo("login") { inclusive = true }
                }
                UserRole.AUTHORITY -> navController.navigate("authority") {
                    popUpTo("login") { inclusive = true }
                }
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Login", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    authViewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            TextButton(
                onClick = {
                    navController.navigate("register")
                }
            ) {
                Text("New user? Register")
            }

            if (state is AuthState.Error) {
                Text(
                    text = (state as AuthState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
