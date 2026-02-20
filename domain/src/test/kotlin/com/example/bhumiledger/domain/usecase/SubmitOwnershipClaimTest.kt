package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import junit.framework.TestCase.assertTrue
import kotlin.test.Test


class SubmitOwnershipClaimTest {

    private class FakeClaimRepository : ClaimRepository {
        private val claims = mutableListOf<OwnershipClaim>()

        override fun getPendingClaimForParcel(parcelId: String): OwnershipClaim? {
            return claims.find {
                it.parcelId == parcelId && it.status == ClaimStatus.PENDING
            }
        }

        override fun saveClaim(claim: OwnershipClaim) {
            claims.add(claim)
        }

        override fun getClaimById(claimId: String): OwnershipClaim? {
            return claims.find { it.id == claimId }
        }

        override fun updateClaim(claim: OwnershipClaim) {}
    }

    // NEW: required because production use case checks ownership existence
    private class FakeRegistryRepository : RegistryRepository {
        override fun save(entry: RegistryEntry) {}
        override fun getByParcelId(parcelId: String): RegistryEntry? = null
        override fun getHistoryForParcel(parcelId: String): List<RegistryEntry> = emptyList()
    }

    @Test
    fun `submitting duplicate pending claim fails`() {

        val claimRepo = FakeClaimRepository()
        val registryRepo = FakeRegistryRepository()

        val useCase = SubmitOwnershipClaim(claimRepo, registryRepo)

        useCase("parcel-1", "user-1")
        val result = useCase("parcel-1", "user-2")

        assertTrue(result is DomainResult.Failure)
    }

    @Test
    fun `submitting valid claim succeeds`() {

        val claimRepo = FakeClaimRepository()
        val registryRepo = FakeRegistryRepository()

        val useCase = SubmitOwnershipClaim(claimRepo, registryRepo)

        val result = useCase("parcel-1", "user-1")

        assertTrue(result is DomainResult.Success)

        val claim = (result as DomainResult.Success).data

        assertTrue(claim.parcelId == "parcel-1")
        assertTrue(claim.claimantId == "user-1")
        assertTrue(claim.status == ClaimStatus.PENDING)
    }
}