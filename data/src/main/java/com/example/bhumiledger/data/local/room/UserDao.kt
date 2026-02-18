package com.example.bhumiledger.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user:UserEntity)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getByEmail(email:String):UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id:String):UserEntity?

}