import android.content.Context
import com.example.bhumiledger.data.local.room.DatabaseProvider
import com.example.bhumiledger.data.repository.RoomClaimRepository
import com.example.bhumiledger.data.repository.RoomRegistryRepository
import com.example.bhumiledger.domain.model.OwnershipClaim
import com.example.bhumiledger.domain.model.UserRole
import com.example.bhumiledger.domain.usecase.CreateRegistryEntry
import com.example.bhumiledger.domain.usecase.GetOwnershipHistory
import com.example.bhumiledger.domain.usecase.SubmitOwnershipClaim
import com.example.bhumiledger.domain.usecase.VerifyOwnershipClaim

class BhumiLedgerContainer(context: Context) {

    private val db =
        DatabaseProvider.getDatabase(context)

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
            registryRepository
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


    fun verifyOwnershipClaim(
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
