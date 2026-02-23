package com.example.bhumiledger.domain.error

sealed class DomainError {
    object DuplicatePendingClaim : DomainError()
    object ClaimNotFound : DomainError()
    object InvalidClaimState : DomainError()
    object OwnershipAlreadyExists : DomainError()
    object UnauthorizedAccess:DomainError()
    object InvalidInput:DomainError()
    object UserAlreadyExists:DomainError()
    object InvalidCredentials : DomainError()
    object AlreadyCurrentOwner:DomainError()


}


