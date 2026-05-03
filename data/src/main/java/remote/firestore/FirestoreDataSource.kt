package remote.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreDataSource {

    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val COLLECTION_CLAIMS = "claims"
    }

    suspend fun addClaim(data: Map<String, Any>): Result<String> {
        return try {
            val docRef = firestore.collection(COLLECTION_CLAIMS).add(data).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Log.e("Firestore" , "Add Claim Failed", e)
            Result.failure(e)
        }
    }
}