package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "territories")
data class TerritoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "territory_id") var territoryId: Int,
    @ColumnInfo(name = "territory_name") var territoryName: String,
    @ColumnInfo(name = "car_id") var carId: Int,
)