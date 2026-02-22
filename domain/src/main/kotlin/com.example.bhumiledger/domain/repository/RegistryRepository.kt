package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.RegistryEntry



interface RegistryRepository {
    suspend fun save(entry: RegistryEntry)
    suspend fun getByParcelId(parcelId: String): RegistryEntry?
   suspend  fun getHistoryForParcel(parcelId: String): List<RegistryEntry>

}



