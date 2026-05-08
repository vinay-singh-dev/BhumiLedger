package com.example.bhumiledger.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bhumiledger.domain.model.SyncState


@Entity(tableName = "claims")
data class ClaimEntity(

    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0,   // database identity

    val claimId: String,     // business identity

    val parcelId: String,
    val claimantId: String,
    val status: String,
    val createdAt: Long,

    val documentPath: String? = null,
    val documentHash:String?,

    val syncState: SyncState
)

