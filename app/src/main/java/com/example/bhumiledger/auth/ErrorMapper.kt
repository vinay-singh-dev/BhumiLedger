package com.example.bhumiledger.auth

import com.example.bhumiledger.domain.error.DomainError

fun DomainError.toMessage(): String {
    return when (this) {
        DomainError.DuplicatePendingClaim -> "A pending claim already exists"
        DomainError.ClaimNotFound -> "Claim not found"
        DomainError.InvalidClaimState -> "Invalid claim state"
        DomainError.OwnershipAlreadyExists -> "Ownership already exists"
        DomainError.UnauthorizedAccess -> "Unauthorized access"
        DomainError.InvalidInput -> "Invalid input"
        DomainError.UserAlreadyExists -> "User already exists"
        DomainError.InvalidCredentials -> "Invalid Credentials"
       DomainError.AlreadyCurrentOwner -> "Already current owner of this Land"
        DomainError.BlockchainCorrupted -> "Blockchain is corrupted. Verification blocked."

    }
}