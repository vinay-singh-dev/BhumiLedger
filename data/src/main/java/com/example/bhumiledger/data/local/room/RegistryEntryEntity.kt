package com.example.bhumiledger.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bhumiledger.domain.model.RegistryEntry


@Entity(tableName = "registry_entries")

data class RegistryEntryEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val parcelId:String,
    val ownerId:String,
    val createdAt: Long

)

fun RegistryEntryEntity.toDomain(): RegistryEntry {

    return RegistryEntry(
        parcelId = parcelId,
        ownerId = ownerId,
        createdAt = createdAt
    )
}

fun RegistryEntry.toEntity():RegistryEntryEntity {
    return RegistryEntryEntity(
        id = 0,
        parcelId = parcelId,
        ownerId = ownerId,
        createdAt = createdAt
    )

}
