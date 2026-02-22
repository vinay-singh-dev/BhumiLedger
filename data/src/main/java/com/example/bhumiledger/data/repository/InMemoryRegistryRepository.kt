package com.example.bhumiledger.data.repository

import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository

class InMemoryRegistryRepository: RegistryRepository {
    private val registry = mutableListOf<RegistryEntry>()

    override suspend  fun save(entry: RegistryEntry) {
        registry.add(entry)
    }

    override suspend fun getByParcelId(parcelId: String): RegistryEntry? {
        return registry.find {it.parcelId == parcelId}
    }

    override suspend  fun getHistoryForParcel(parcelId: String): List<RegistryEntry> {
        return registry.filter {it.parcelId == parcelId }
    }
}