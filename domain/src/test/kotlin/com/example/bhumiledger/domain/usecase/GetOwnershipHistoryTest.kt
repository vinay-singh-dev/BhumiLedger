package com.example.bhumiledger.domain.usecase

import com.example.bhumiledger.domain.model.RegistryEntry
import com.example.bhumiledger.domain.repository.RegistryRepository
import com.example.bhumiledger.domain.result.DomainResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetOwnershipHistoryTest {

    @Test
    fun `get ownership history for a parcel`() {
        val repo = FakeRegistryRepository()

        val entry1 = RegistryEntry(
            parcelId = "parcel-1",
            ownerId = "user-1",
            createdAt = 1000L
        )

        val entry2 = RegistryEntry(
            parcelId = "parcel-2",
            ownerId = "user-2",
            createdAt = 2000L
        )
        repo.save(entry1)
        repo.save(entry2)

        val  useCase = GetOwnershipHistory(repo)

        val result = useCase("parcel-1")

        assertTrue(result is DomainResult.Success)

        val history = (result as DomainResult.Success).data

        assertEquals(1,history.size)
        assertEquals("user-1",history[0].ownerId)


    }

    @Test
    fun `return empty history if the parcel has no entries` (){

        val repo = FakeRegistryRepository()

        val useCase = GetOwnershipHistory(repo)

        val result = useCase("parcel-23")

        assertTrue(result is DomainResult.Success)

        val history = (result as DomainResult.Success).data

        assertEquals(0,history.size)


    }

    @Test

    fun `return only entries for requested parcel` () {

        val repo = FakeRegistryRepository()


        val entry1 = RegistryEntry(
            parcelId = "parcel-4",
            ownerId = "user-1",
            createdAt = 1000L
        )

        val entry2 = RegistryEntry (

            parcelId = "parcel-8",
            ownerId = "user-6",
            createdAt = 2000L
        )

        repo.save(entry1)
        repo.save(entry2)

        val useCase = GetOwnershipHistory(repo)

        val result  = useCase("parcel-4")

        assertTrue(result is DomainResult.Success)

        val history = (result as DomainResult.Success).data

        assertEquals(1,history.size)
        assertEquals("user-1",history[0].ownerId)

    }

    @Test

    fun `preserves ownership order for parcel history` () {
        val repo = FakeRegistryRepository()

        val entry1 = RegistryEntry(
            parcelId = "parcel-1",
            ownerId = "user-A",
            createdAt = 1000L
        )

        val entry2 = RegistryEntry(
            parcelId = "parcel-1",
            ownerId = "user-B",
            createdAt = 2000L
        )

        val entry3 = RegistryEntry(
            parcelId = "parcel-1",
            ownerId = "user-C",
            createdAt = 3000L
        )

        repo.save(entry1)
        repo.save(entry2)
        repo.save(entry3)

        val useCase = GetOwnershipHistory(repo)

        val result = useCase("parcel-1")

        assertTrue(result is  DomainResult.Success)

        val history = (result as DomainResult.Success).data

        assertEquals(3,history.size)

        assertEquals("user-A",history[0].ownerId)
        assertEquals("user-B",history[1].ownerId)
        assertEquals("user-C",history[2].ownerId)

    }


}
