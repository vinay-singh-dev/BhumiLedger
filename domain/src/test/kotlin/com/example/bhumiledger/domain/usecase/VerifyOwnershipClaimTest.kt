package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.ClaimStatus
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.repository.ClaimRepository
import com.example.bhumiledger.domain.result.DomainResult
import junit.framework.TestCase.assertTrue
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


        }


        override fun getClaimById(claimId: String): OwnershipClaim? {
            for (claim in claims)
                if(claim.id == claimId)
                    return claim
            return null
        }

        override fun updateClaim(claim: OwnershipClaim) {

        }

    }

    @Test
    fun `verifying ownership claim`() {
        val repo = VerifyClaimRepository()
        val uses = VerifyOwnershipClaim(repo)


    }
    }