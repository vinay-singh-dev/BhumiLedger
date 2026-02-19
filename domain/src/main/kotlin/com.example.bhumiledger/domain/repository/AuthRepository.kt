package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.User

interface AuthRepository {

    suspend fun registerUser(user: User)

    suspend fun loginUser(
        email: String,
        passwordHash: String
    ): User?

    suspend fun getUserById(id: String): User?
}
