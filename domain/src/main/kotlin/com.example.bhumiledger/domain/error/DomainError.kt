package domain.error

sealed class DomainError {
    object DuplicatePendingClaim : DomainError()
    object ClaimNotFound : DomainError()
}