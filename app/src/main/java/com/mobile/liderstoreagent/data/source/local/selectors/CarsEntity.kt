package com.mobile.liderstoreagent.data.source.local.selectors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarsEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "car_id") var carId: Int,
    @ColumnInfo(name = "name") var name: String,
)