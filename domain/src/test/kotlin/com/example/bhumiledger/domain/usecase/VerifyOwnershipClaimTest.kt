package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult
import junit.framework.TestCase.assertTrue
import java.io.ObjectInputFilter
import kotlin.test.Test

class VerifyOwnershipClaimTest {

    private class VerifyClaimRepository : ClaimRepository {
        private val claims = mutableListOf<OwnershipClaim>()
        override fun getPendingClaimForParcel(parcelId: String): OwnershipClaim? {
            return claims.find{
                it.parcelId == parcelId && it.status == ClaimStatus.PENDING
            }
        }

        override fun saveClaim(claim: OwnershipClaim) {
            claims.add(claim)


        }


        override fun getClaimById(claimId: String): OwnershipClaim? {
            for (claim in claims)
                if(claim.id == claimId)
                    return claim
            return null
        }

        override fun updateClaim(claim: OwnershipClaim) {
           val index = claims.indexOfFirst{it.id == claim.id}
            if(index != -1)
                claims[index] =claim



        }

    }

    @Test
    fun `verifying a claim and changing its status to verified `() {
        val repo = VerifyClaimRepository()
        val useCase = VerifyOwnershipClaim(repo)

        // Arrange
        val pendingClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-1",
            claimantId = "user-1",
            status = ClaimStatus.PENDING
        )
        repo.saveClaim(pendingClaim)

        // Act
        val result = useCase("claim-1")

        // Assert
        assertTrue(result is DomainResult.Success)
        val verifiedClaim = (result as DomainResult.Success).data
        assertTrue(verifiedClaim.status == ClaimStatus.VERIFIED)
    }

    @Test
    fun `Verify non pending claim`() {
        val repo = VerifyClaimRepository()
        val useCase = VerifyOwnershipClaim(repo)

        val verifiedClaim = OwnershipClaim(
            id = "claim-1",
            parcelId = "parcel-2",
            claimantId = "user-1",
            status = ClaimStatus.VERIFIED
        )
        repo.saveClaim(verifiedClaim)

        val result = useCase("claim-1")
        assertTrue(result is DomainResult.Failure)

    }

    @Test
    fun `Claim does not exist so failure`(){
        val repo = VerifyClaimRepository()
        val useCase = VerifyOwnershipClaim(repo)
        val result = useCase("claim-69")
        assertTrue(result is DomainResult.Failure)





    }
}