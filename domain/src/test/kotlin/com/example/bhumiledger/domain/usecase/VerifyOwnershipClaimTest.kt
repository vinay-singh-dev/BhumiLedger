package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import junit.framework.TestCase.assertTrue
import java.io.ObjectInputFilter
import kotlin.test.Test


class VerifyOwnershipClaimTest {

    // Fake Claim Repository
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

        override fun updateClaim(claim: OwnershipClaim) {
            val index = claims.indexOfFirst { it.id == claim.id }
            if (index != -1) claims[index] = claim
        }
    }

    // NEW: Fake Registry Repository required because
    // production use case now depends on it
    private class FakeRegistryRepository : RegistryRepository {
        override fun save(entry: RegistryEntry) {}
        override fun getByParcelId(parcelId: String): RegistryEntry? = null
        override fun getHistoryForParcel(parcelId: String): List<RegistryEntry> = emptyList()
    }

    @Test
    fun `authority verifies pending claim successfully`() {

        val claimRepo = FakeClaimRepository()
        val registryRepo = FakeRegistryRepository()

        // FIX: Pass both repositories
        val useCase = VerifyOwnershipClaim(claimRepo, registryRepo)

        val pendingClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-1",
            claimantId = "user-1",
            status = ClaimStatus.PENDING
        )

        claimRepo.saveClaim(pendingClaim)

        // FIX: Must pass role now
        val result = useCase("claim-1", UserRole.AUTHORITY)

        assertTrue(result is DomainResult.Success)
        val verifiedClaim = (result as DomainResult.Success).data
        assertTrue(verifiedClaim.status == ClaimStatus.VERIFIED)
    }

    @Test
    fun `citizen cannot verify claim`() {

        val claimRepo = FakeClaimRepository()
        val registryRepo = FakeRegistryRepository()
        val useCase = VerifyOwnershipClaim(claimRepo, registryRepo)

        val pendingClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-1",
            claimantId = "user-1",
            status = ClaimStatus.PENDING
        )

        claimRepo.saveClaim(pendingClaim)

        val result = useCase("claim-1", UserRole.CITIZEN)

        assertTrue(result is DomainResult.Failure)
    }

    @Test
    fun `non pending claim verification fails`() {

        val claimRepo = FakeClaimRepository()
        val registryRepo = FakeRegistryRepository()
        val useCase = VerifyOwnershipClaim(claimRepo, registryRepo)

        val verifiedClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-2",
            claimantId = "user-1",
            status = ClaimStatus.VERIFIED
        )

        claimRepo.saveClaim(verifiedClaim)

        val result = useCase("claim-1", UserRole.AUTHORITY)

        assertTrue(result is DomainResult.Failure)
    }

    @Test
    fun `non existing claim fails`() {

        val claimRepo = FakeClaimRepository()
        val registryRepo = FakeRegistryRepository()
        val useCase = VerifyOwnershipClaim(claimRepo, registryRepo)

        val result = useCase("claim-69", UserRole.AUTHORITY)

        assertTrue(result is DomainResult.Failure)
    }
}