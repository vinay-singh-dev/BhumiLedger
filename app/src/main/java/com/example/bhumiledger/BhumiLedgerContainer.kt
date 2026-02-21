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
import com.example.bhumiledger.domain.usecase.GetClaimsByUserUseCase
import com.example.bhumiledger.domain.usecase.GetOwnershipHistory
import com.example.bhumiledger.domain.usecase.GetPendingClaimsUseCase
import com.example.bhumiledger.domain.usecase.LoginUserUseCase
import com.example.bhumiledger.domain.usecase.RegisterUserUseCase
import com.example.bhumiledger.domain.usecase.SubmitOwnershipClaim
import com.example.bhumiledger.domain.usecase.VerifyOwnershipClaim
import com.example.bhumiledger.session.SessionManager

class BhumiLedgerContainer(context: Context) {

    private val db =
        DatabaseProvider.getDatabase(context)

    // --- DAO ---
    private val blockDao = db.blockDao()
    private val claimDao = db.claimDao()
    private val registryDao = db.registryDao()
    private val userDao = db.userDao()

    // --- Repositories ---
    private val blockchainRepository =
        RoomBlockchainRepository(blockDao)

    private val claimRepository =
        RoomClaimRepository(claimDao)



    private val registryRepository =
        RoomRegistryRepository(registryDao)

    private val authRepository =
        RoomAuthRepository(userDao)

    // --- Session ---
    val sessionManager: SessionManager by lazy {
        SessionManager(context)
    }

    // --- UseCases ---
    val loginUserUseCase =
        LoginUserUseCase(authRepository)

    val registerUserUseCase =
        RegisterUserUseCase(authRepository)

    val getPendingClaimsUseCase =
        GetPendingClaimsUseCase(claimRepository)

    val getClaimsByUserUseCase =
        GetClaimsByUserUseCase(claimRepository)

    private val submitOwnershipClaimUseCase =
        SubmitOwnershipClaim(
            claimRepository,
            registryRepository
        )

    private val verifyOwnershipClaimUseCase =
        VerifyOwnershipClaim(
            claimRepository,
            registryRepository,
            blockchainRepository
        )

    private val createRegistryEntryUseCase =
        CreateRegistryEntry(registryRepository)

    private val getOwnershipHistoryUseCase =
        GetOwnershipHistory(registryRepository)

    // --- Exposed functions ---

    suspend fun submitOwnershipClaim(
        parcelId: String,
        claimantId: String
    ) =
        submitOwnershipClaimUseCase(parcelId, claimantId)

    suspend fun getPendingClaims() =
        getPendingClaimsUseCase()

    suspend fun verifyOwnershipClaim(
        claimId: String,
        role: UserRole
    ) =
        verifyOwnershipClaimUseCase(claimId, role)

    fun createRegistryEntry(
        claim: OwnershipClaim
    ) =
        createRegistryEntryUseCase(claim)

    fun getOwnershipHistory(
        parcelId: String
    ) =
        getOwnershipHistoryUseCase(parcelId)

    suspend fun getClaimsByUser(userId: String) =
        getClaimsByUserUseCase(userId)
}