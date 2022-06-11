package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_type")
data class MarketTypeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "market_id") var marketId: Int,
    @ColumnInfo(name = "name") var name: String,
)