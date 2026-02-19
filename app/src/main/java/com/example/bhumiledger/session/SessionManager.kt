package com.example.bhumiledger.session

import android.content.Context
import android.content.SharedPreferences
import com.example.bhumiledger.domain.model.UserRole

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("bhumiledger_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ROLE = "user_role"
    }

    fun saveSession(userId: String, role: UserRole) {
        prefs.edit()
            .putString(KEY_USER_ID, userId)
            .putString(KEY_ROLE, role.name)
            .apply()
    }

    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun getUserRole(): UserRole? {
        val roleName = prefs.getString(KEY_ROLE, null)
        return roleName?.let { UserRole.valueOf(it) }
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return getUserId() != null
    }
}
