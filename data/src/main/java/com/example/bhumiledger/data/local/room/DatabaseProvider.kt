package com.example.bhumiledger.data.local.room

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: BhumiLedgerDatabase? = null

    fun getDatabase(context: Context): BhumiLedgerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                BhumiLedgerDatabase::class.java,
                "bhumi_ledger_db"
            )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()

            INSTANCE = instance
            instance
        }
    }
}
