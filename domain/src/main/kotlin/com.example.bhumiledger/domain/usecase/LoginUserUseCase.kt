package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.repository.AuthRepository
import com.example.bhumiledger.domain.result.DomainResult


class LoginUserUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        passwordHash: String
    ): DomainResult<User> {

        if (email.isBlank() || passwordHash.isBlank()) {
            return DomainResult.Failure(DomainError.InvalidInput)
        }

        val user = authRepository.loginUser(email, passwordHash)
            ?: return DomainResult.Failure(DomainError.InvalidCredentials)

        return DomainResult.Success(user)
    }
}