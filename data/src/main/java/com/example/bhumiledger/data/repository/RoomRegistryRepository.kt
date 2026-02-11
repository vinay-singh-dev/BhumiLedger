package com.example.bhumiledger.data.repository

import com.example.bhumiledger.data.local.room.RegistryDao
import com.example.bhumiledger.data.local.room.toDomain
import com.example.bhumiledger.data.local.room.toEntity
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository
import kotlinx.coroutines.runBlocking

class RoomRegistryRepository(
    private val dao: RegistryDao
) : RegistryRepository {

    override fun save(entry: RegistryEntry) {
        runBlocking {
            dao.insert(entry.toEntity())
        }
    }

    override fun getByParcelId(parcelId: String): RegistryEntry? {
        return runBlocking {
            dao.getLatestForParcel(parcelId)?.toDomain()
        }
    }

    override fun getHistoryForParcel(parcelId: String): List<RegistryEntry> {
        return runBlocking {
            dao.getHistoryForParcel(parcelId)
                .map { it.toDomain() }
        }
    }
}
