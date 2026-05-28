package com.example.bhumiledger.data.remote.auth
import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
class FirebaseAuthDataSource (
    privat val firebaseAuth :FirebaseAuth
):AuthRepository
    override suspend fun registerUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }
