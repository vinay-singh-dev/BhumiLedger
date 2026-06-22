package com.example.bhumiledger.domain.model

data class User (
    val name:String,
    val id:String,
    val email:String,
    val employeeId:String?,
    val role: UserRole

)
