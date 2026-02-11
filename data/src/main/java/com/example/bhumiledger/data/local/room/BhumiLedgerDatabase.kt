package com.example.bhumiledger.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        RegistryEntryEntity::class,
        ClaimEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BhumiLedgerDatabase : RoomDatabase() {

    abstract fun registryDao(): RegistryDao

    abstract fun claimDao(): ClaimDao
}
