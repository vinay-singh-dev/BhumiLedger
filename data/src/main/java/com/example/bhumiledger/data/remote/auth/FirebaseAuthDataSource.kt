package com.example.bhumiledger.data.remote.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource (
    private val firebaseAuth :FirebaseAuth
) {
    suspend fun registerUser(
        email: String,
        password: String
    ): FirebaseUser? {

        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(email,password)
            .await()
        return authResult.user
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): FirebaseUser? {
        val signInResult = firebaseAuth
            .signInWithEmailAndPassword(email,password)
            .await()
        return signInResult.user
    }

    fun logout(): Unit {
        firebaseAuth.
                signOut()
    }

     fun getCurrentUser(): FirebaseUser? {
       return  firebaseAuth.currentUser
    }
}

