package com.example.bhumiledger.data.remote.firestore.dto

import com.example.bhumiledger.domain.model.UserRole

data class UserDto (
    val uid : String,
    val name : String,
    val email: String,
    val role : UserRole,
    val employeeId: String?
)
