package com.example.bhumiledger.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bhumiledger.data.local.converter.SyncStateConverter

@Database(
    entities = [
        ClaimEntity::class,
        RegistryEntryEntity::class,
        UserEntity::class,
        BlockEntity::class
    ],
    version = 5,   // 👈 bump version
    exportSchema = false
)

@TypeConverters(SyncStateConverter::class)
abstract class BhumiLedgerDatabase : RoomDatabase() {

    abstract fun registryDao(): RegistryDao

    abstract fun claimDao(): ClaimDao

    abstract fun userDao(): UserDao

    abstract fun blockDao(): BlockDao

}
