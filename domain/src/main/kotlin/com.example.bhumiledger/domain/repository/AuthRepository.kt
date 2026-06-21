package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.result.DomainResult

interface AuthRepository {

    suspend fun registerUser(
        name: String,
        email: String,
        password :String,
        role: UserRole) : DomainResult<User>

    suspend fun loginUser(
        email: String,
        password :String
    ): DomainResult<User>

    suspend fun getUserById(id: String): DomainResult<User>

     fun logout()

    suspend fun getCurrentUser(): DomainResult<User>

}
