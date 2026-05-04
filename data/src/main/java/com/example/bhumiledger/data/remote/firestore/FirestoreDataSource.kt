package com.example.bhumiledger.data.remote.firestore

import android.R.attr.data
import android.util.Log
import com.example.bhumiledger.data.remote.firestore.dto.ClaimDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreDataSource {

    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val COLLECTION_CLAIMS = "claims"
    }

    suspend fun addClaim(dto : ClaimDto): Result<String> {
        return try {
            val docRef = firestore
                .collection(COLLECTION_CLAIMS)
                .add(dto)
                .await()
            Result.success(docRef.id)
        } catch ( e :Exception ) {
            Result.failure(e)
        }

    }
}