package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.repository.AuthRepository
import java.util.UUID

class RegisterUserUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        passwordHash: String,
        role: UserRole
    ): User {

        val user = User(
            id = UUID.randomUUID().toString(),
            name = name,
            email = email,
            passwordHash = passwordHash,
            role = role
        )

        authRepository.registerUser(user)

        return user
    }
}
