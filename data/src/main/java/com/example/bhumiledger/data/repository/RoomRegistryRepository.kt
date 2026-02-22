package com.example.bhumiledger.data.repository

import android.util.Log
import com.example.bhumiledger.data.local.room.RegistryDao
import com.example.bhumiledger.data.local.room.toDomain
import com.example.bhumiledger.data.local.room.toEntity
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository

class RoomRegistryRepository(
    private val dao: RegistryDao
) : RegistryRepository {

    override suspend fun save(entry: RegistryEntry) {

        Log.d("ROOM_TEST", "saveRegistryEntry called: $entry")

        dao.insert(entry.toEntity())

        Log.d("ROOM_TEST", "saveRegistryEntry SUCCESS")
    }

    override suspend fun getByParcelId(parcelId: String): RegistryEntry? {

        Log.d("ROOM_TEST", "getByParcelId called: $parcelId")

        val entity = dao.getLatestForParcel(parcelId)

        Log.d("ROOM_TEST", "getByParcelId result: $entity")

        return entity?.toDomain()
    }

    override suspend fun getHistoryForParcel(parcelId: String): List<RegistryEntry> {

        Log.d("ROOM_TEST", "getHistoryForParcel called: $parcelId")

        val entities = dao.getHistoryForParcel(parcelId)

        Log.d("ROOM_TEST", "getHistoryForParcel result count: ${entities.size}")

        return entities.map { it.toDomain() }
    }
}