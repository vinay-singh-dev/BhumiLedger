package com.example.bhumiledger.domain.repository

import com.example.bhumiledger.domain.model.RegistryEntry



interface RegistryRepository {
    fun save(entry: RegistryEntry)
    fun getByParcelId(parcelId: String): RegistryEntry?
}



