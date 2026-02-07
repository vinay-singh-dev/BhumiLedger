package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import java.util.Map.entry

class GetOwnershipHistory(
    private val registryRepository : RegistryRepository) {
    operator fun invoke (parcelId: String) : DomainResult<List<RegistryEntry>> {
        val history = registryRepository.getHistoryForParcel(parcelId)
        return DomainResult.Success(history)
    }


}






