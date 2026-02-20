package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.UserDao
import com.example.bhumiledger.data.mapper.toDomain
import com.example.bhumiledger.data.mapper.toEntity
import com.example.bhumiledger.domain.model.User
import com.example.bhumiledger.domain.repository.AuthRepository

class RoomAuthRepository(
    private val userDao: UserDao
): AuthRepository {

    override suspend fun registerUser(user: User) {

        userDao.insert(user.toEntity())

    }

    override suspend fun loginUser(email:String ,passwordHash:String):User? {

        val entity = userDao.getByEmail(email)?:return null

        if(entity.passwordHash != passwordHash) {
            return null

        }

            return entity.toDomain()
        }

    override suspend fun getUserById(id:String):User? {
        return userDao.getById(id)?.toDomain()

    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getByEmail(email)?.toDomain()
    }


}