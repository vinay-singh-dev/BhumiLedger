package com.example.bhumiledger.domain.result

import com.example.bhumiledger.domain.error.DomainError

sealed class DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Failure(val error: DomainError) : DomainResult<Nothing>()
}
