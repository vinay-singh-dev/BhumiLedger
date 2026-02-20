package com.example.bhumiledger.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.result.DomainResult
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
        _authState.value = AuthState.Idle
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

            when (val result = registerUserUseCase(
                name,
                email,
                passwordHash,
                role
            )) {

                is DomainResult.Success -> {

                    val user = result.data

                    sessionManager.saveSession(
                        user.id,
                        user.role
                    )

                    _authState.value = AuthState.Success
                }

                is DomainResult.Failure -> {
                    _authState.value =
                        AuthState.Error(result.error.toMessage())
                }
            }
        }
    }



    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            when (val result = loginUserUseCase(email, passwordHash)) {

                is DomainResult.Success -> {
                    val user = result.data
                    sessionManager.saveSession(user.id, user.role)
                    _authState.value = AuthState.Success
                }

                is DomainResult.Failure -> {
                    _authState.value =
                        AuthState.Error(result.error.toMessage())
                }
            }
        }
    }
    }
