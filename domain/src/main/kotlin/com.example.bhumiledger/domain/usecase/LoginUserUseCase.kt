package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.repository.AuthRepository

class LoginUserUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        passwordHash: String
    ): User? {

        return authRepository.loginUser(email, passwordHash)
    }
}
