package domain.usecase

import domain.model.ClaimStatus
import domain.model.OwnershipClaim
import domain.error.DomainError
import domain.repository.ClaimRepository
import domain.result.DomainResult
import java.util.UUID


class SubmitOwnershipClaim(
    private val claimRepository: ClaimRepository
) {
    operator fun invoke(
        parcelId: String,
        claimantId: String
    ): DomainResult<OwnershipClaim> {

        val existing = claimRepository.getPendingClaimForParcel(parcelId)
        if (existing != null) {
            return DomainResult.Failure(DomainError.DuplicatePendingClaim)
        }

        val claim = OwnershipClaim(
            id = UUID.randomUUID().toString(),
            parcelId = parcelId,
            claimantId = claimantId,
            status = ClaimStatus.PENDING
        )

        claimRepository.saveClaim(claim)
        return DomainResult.Success(claim)
    }
}
