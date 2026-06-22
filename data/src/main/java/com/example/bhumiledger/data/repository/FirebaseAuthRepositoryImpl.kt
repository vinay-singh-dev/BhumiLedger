package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.UserDao
import com.example.bhumiledger.data.remote.auth.FirebaseAuthDataSource
import com.example.bhumiledger.data.remote.firestore.dto.UserDto
import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.repository.AuthRepository
import com.example.bhumiledger.domain.result.DomainResult

class FirebaseAuthRepositoryImpl (
    ): AuthRepository {


    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): DomainResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): DomainResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): DomainResult<User> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): DomainResult<User> {
        TODO("Not yet implemented")
    }


}