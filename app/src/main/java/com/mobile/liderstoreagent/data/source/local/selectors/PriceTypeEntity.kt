package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_type")
data class PriceTypeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "price_id") var priceId: Int,
    @ColumnInfo(name = "type") var type: String,
)