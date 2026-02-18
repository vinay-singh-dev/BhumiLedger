package com.example.bhumiledger.domain.model

data class User (
    val name:String,
    val id:String,
    val email:String,
    val passwordHash:String,
    val role: UserRole

)
