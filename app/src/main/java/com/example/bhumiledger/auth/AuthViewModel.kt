package com.example.bhumiledger.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.usecase.LoginUserUseCase
import com.example.bhumiledger.domain.usecase.RegisterUserUseCase
import com.example.bhumiledger.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun logout() {
        sessionManager.clearSession()
    }
    fun getCurrentUserId(): String? {
        return sessionManager.getUserId()
    }

    fun getCurrentUserRole(): UserRole? {
        return sessionManager.getUserRole()
    }



    fun register(
        name: String,
        email: String,
        passwordHash: String,
        role: UserRole
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                val user = registerUserUseCase(
                    name,
                    email,
                    passwordHash,
                    role
                )

                // Auto login after register
                sessionManager.saveSession(user.id, user.role)

                _authState.value = AuthState.Success

            } catch (e: Exception) {
                _authState.value = AuthState.Error(
                    e.message ?: "Registration failed"
                )
            }
        }
    }


    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val user = loginUserUseCase(email, passwordHash)

            if (user != null) {
                sessionManager.saveSession(user.id, user.role)
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Invalid credentials")
            }
        }
    }
}
