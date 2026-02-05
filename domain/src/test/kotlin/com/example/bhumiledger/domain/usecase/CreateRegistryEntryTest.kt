package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.error.DomainError
import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import kotlin.test.Test
import kotlin.test.assertTrue

internal class FakeRegistryRepository : RegistryRepository {
    private val registry = mutableListOf<RegistryEntry>()

    override fun save(entry: RegistryEntry) {
        registry.add(entry)
    }

    override fun getByParcelId(parcelId: String): RegistryEntry? {
        return registry.find { it.parcelId == parcelId }
    }
}

class CreateRegistryEntryTest {

    @Test
    fun `creating registry entry from verified claim succeeds`() {
        val repo = FakeRegistryRepository()
        val useCase = CreateRegistryEntry(repo)

        val verifiedClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-2",
            claimantId = "user-1",
            status = ClaimStatus.VERIFIED
        )

        val result = useCase(verifiedClaim)

        assertTrue(result is DomainResult.Success)

        val entry = (result as DomainResult.Success).data
        assertTrue(entry.parcelId == "parcel-2")
        assertTrue(entry.ownerId == "user-1")
    }

    @Test
    fun `creating registry entry when ownership already exists returns failure` () {
        val repo = FakeRegistryRepository()
        val useCase = CreateRegistryEntry(repo)

        val verifiedClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-p1",
            claimantId = "user-1",
            status = ClaimStatus.VERIFIED
        )
        val existingEntry = RegistryEntry(
            parcelId = "parcel-p1",
            ownerId = "user-1",
            createdAt = System.currentTimeMillis()
        )
        repo.save(existingEntry)

        val result = useCase(verifiedClaim)

        assertTrue(result is DomainResult.Failure)
       assertTrue(result.error == DomainError.OwnershipAlreadyExists)




    }
}
