package com.example.bhumiledger.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bhumiledger.domain.model.UserRole

@Entity("users")


data class UserEntity(
    @PrimaryKey
    val id:String,
    val name:String,
    val email:String,
    val passwordHash:String,
    val role: UserRole

)
