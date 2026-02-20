package com.example.bhumiledger

import android.content.Context
import com.example.bhumiledger.data.local.room.DatabaseProvider
import com.example.bhumiledger.data.repository.RoomAuthRepository
import com.example.bhumiledger.data.repository.RoomBlockchainRepository
import com.example.bhumiledger.data.repository.RoomClaimRepository
import com.example.bhumiledger.data.repository.RoomRegistryRepository
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.usecase.CreateRegistryEntry
import com.example.bhumiledger.domain.usecase.GetOwnershipHistory
import com.example.bhumiledger.domain.usecase.LoginUserUseCase
import com.example.bhumiledger.domain.usecase.RegisterUserUseCase
import com.example.bhumiledger.domain.usecase.SubmitOwnershipClaim
import com.example.bhumiledger.domain.usecase.VerifyOwnershipClaim
import com.example.bhumiledger.session.SessionManager

class BhumiLedgerContainer(context: Context) {

    private val db =
        DatabaseProvider.getDatabase(context)

    private val blockDao = db.blockDao()

    private val blockchainRepository =
        RoomBlockchainRepository(blockDao)

    private val userDao = db.userDao()

    private val authRepository =
        RoomAuthRepository(userDao)

    val loginUserUseCase =
        LoginUserUseCase(authRepository)

    val registerUserUseCase =
        RegisterUserUseCase(authRepository)

    val sessionManager: SessionManager by lazy {
        SessionManager(context)
    }



    private val claimRepository =
        RoomClaimRepository(db.claimDao())

    private val registryRepository =
        RoomRegistryRepository(db.registryDao())


    // FIXED
    private val submitOwnershipClaimUseCase =
        SubmitOwnershipClaim(
            claimRepository,
            registryRepository
        )


    // FIXED (THIS WAS WRONG)
    private val verifyOwnershipClaimUseCase =
        VerifyOwnershipClaim(
            claimRepository,
            registryRepository,
            blockchainRepository
        )


    private val createRegistryEntryUseCase =
        CreateRegistryEntry(
            registryRepository
        )

    private val getOwnershipHistoryUseCase =
        GetOwnershipHistory(
            registryRepository
        )


    fun submitOwnershipClaim(
        parcelId: String,
        claimantId: String
    ) =
        submitOwnershipClaimUseCase(
            parcelId,
            claimantId
        )


    suspend fun verifyOwnershipClaim(
        claimId: String,
        role: UserRole
    ) =
        verifyOwnershipClaimUseCase(
            claimId,
            role
        )


    fun createRegistryEntry(
        claim: OwnershipClaim
    ) =
        createRegistryEntryUseCase(
            claim
        )


    fun getOwnershipHistory(
        parcelId: String
    ) =
        getOwnershipHistoryUseCase(
            parcelId
        )
}