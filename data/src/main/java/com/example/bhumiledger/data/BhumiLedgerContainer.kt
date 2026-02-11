import android.content.Context
import com.example.bhumiledger.data.local.room.DatabaseProvider
import com.example.bhumiledger.data.repository.RoomClaimRepository
import com.example.bhumiledger.data.repository.RoomRegistryRepository
import com.example.bhumiledger.domain.usecase.CreateRegistryEntry
import com.example.bhumiledger.domain.usecase.GetOwnershipHistory
import com.example.bhumiledger.domain.usecase.SubmitOwnershipClaim
import com.example.bhumiledger.domain.usecase.VerifyOwnershipClaim

class BhumiLedgerContainer(context: Context) {

    private val db = DatabaseProvider.getDatabase(context)

    val claimRepository = RoomClaimRepository(db.claimDao())

    val registryRepository = RoomRegistryRepository(db.registryDao())

    val submitOwnershipClaim =
        SubmitOwnershipClaim(claimRepository)

    val verifyOwnershipClaim =
        VerifyOwnershipClaim(claimRepository)

    val createRegistryEntry =
        CreateRegistryEntry(registryRepository)

    val getOwnershipHistory =
        GetOwnershipHistory(registryRepository)
}
