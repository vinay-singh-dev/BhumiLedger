package com.example.bhumiledger.data.mapper

import com.example.bhumiledger.data.local.room.UserEntity
import com.example.bhumiledger.data.remote.firestore.dto.UserDto
import com.example.bhumiledger.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        employeeId = employeeId,
        role = role

    )

}

fun User.toEntity():UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        employeeId = employeeId,
        role = role
    )
}

    fun User.toDto(): UserDto {
        return UserDto (
        uid = id,
        name = name,
        email = email,
        employeeId = employeeId,
        role = role
        )
    }


    fun UserDto.toDomain(): User {
        return User (
        id = uid,
        name = name,
        email = email,
        employeeId = employeeId,
        role = role
        )
    }




