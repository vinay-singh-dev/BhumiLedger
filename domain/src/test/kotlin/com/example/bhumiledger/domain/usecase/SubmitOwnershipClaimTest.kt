package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
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
            for(claim in claims )
                if(claim.id == claimId)
                    return claim
            return null

        }

        override fun updateClaim(claim: OwnershipClaim) {

        }
    }

    @Test
    fun `submitting duplicate pending claim fails`() {
        val repo = FakeClaimRepository()
        val useCase = SubmitOwnershipClaim(repo)

        useCase("parcel-1", "user-1")
        val result = useCase("parcel-1", "user-2")

        assertTrue(result is DomainResult.Failure)
    }

    @Test
    fun `submitting valid claim succeeds`() {
        val repo = FakeClaimRepository()
        val useCase = SubmitOwnershipClaim(repo)
       useCase("parcel-1", "user-1")

       val result = repo.getPendingClaimForParcel("parcel-1")
        assert(result != null)
        val claim = result!!
                assertTrue(claim.parcelId == "parcel-1")
                assertTrue(claim.claimantId == "user-1")
        assertTrue(claim.status == ClaimStatus.PENDING)



    }
}