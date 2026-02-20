package com.example.bhumiledger.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ClaimEntity::class,
        RegistryEntryEntity::class,
        UserEntity::class,
        BlockEntity::class
    ],
    version = 3,   // 👈 bump version
    exportSchema = false
)
abstract class BhumiLedgerDatabase : RoomDatabase() {

    abstract fun registryDao(): RegistryDao

    abstract fun claimDao(): ClaimDao

    abstract fun userDao(): UserDao

    abstract fun blockDao(): BlockDao

}
