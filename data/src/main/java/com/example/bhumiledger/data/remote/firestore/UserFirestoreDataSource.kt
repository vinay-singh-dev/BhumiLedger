package com.example.bhumiledger.data.remote.firestore

import android.util.Log
import com.example.bhumiledger.data.remote.firestore.dto.UserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserFirestoreDataSource {

    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val COLLECTION_USERS = "users"
    }

    suspend fun saveUser(dto: UserDto): Result<String> {
        return try {
              firestore
                .collection(COLLECTION_USERS)
                .document(dto.uid)
                .set(dto)
                .await()
            Result.success(dto.uid)
        } catch (e: Exception) {
            Log.e("UserStore","Upload failed",e)
            Result.failure(e)
        }
    }

}