package com.example.bhumiledger.data.mapper

import com.example.bhumiledger.data.local.room.UserEntity
import com.example.bhumiledger.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        passwordHash = passwordHash,
        role = role

    )

}

fun User.toEntity():UserEntity {
    return UserEntity (
        id = id,
        name = name,
        email = email,
        passwordHash = passwordHash,
        role = role
    )

}