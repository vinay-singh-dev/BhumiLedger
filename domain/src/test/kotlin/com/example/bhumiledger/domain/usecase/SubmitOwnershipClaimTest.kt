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
    }

    @Test
    fun `submitting duplicate pending claim fails`() {
        val repo = FakeClaimRepository()
        val useCase = SubmitOwnershipClaim(repo)

        useCase("parcel-1", "user-1")
        val result = useCase("parcel-1", "user-2")

        assertTrue(result is DomainResult.Failure)
    }
}