package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.repository.AuthRepository
import com.example.bhumiledger.domain.result.DomainResult
import java.util.UUID

class RegisterUserUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        passwordHash: String,
        role: UserRole
    ): DomainResult<User> {

        if (name.isBlank() || email.isBlank() || passwordHash.isBlank()) {
            return DomainResult.Failure(DomainError.InvalidInput)
        }

        val existingUser = authRepository.getUserByEmail(email)
        if (existingUser != null) {
            return DomainResult.Failure(DomainError.UserAlreadyExists)
        }

        val user = User(
            id = UUID.randomUUID().toString(),
            name = name,
            email = email,
            employeeId = employeeId,
            role = role
        )

        authRepository.registerUser(user)

        return DomainResult.Success(user)
    }
}
