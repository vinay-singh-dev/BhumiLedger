package com.example.bhumiledger.data.local.converter

import androidx.room.TypeConverter
import com.example.bhumiledger.domain.model.SyncState

class SyncStateConverter {

    @TypeConverter
    fun fromSyncState(state: SyncState):String {
        return state.name
    }

    @TypeConverter
    fun toSyncState(value:String):SyncState {
        return SyncState.valueOf(value)
    }

}