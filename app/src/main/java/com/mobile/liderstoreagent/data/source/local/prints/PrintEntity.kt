package com.mobile.liderstoreagent.data.source.local.prints

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prints")
data class PrintEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "clientId") var clientId: Int,
    @ColumnInfo(name = "initialDebt") var initialDebt: Double,
    @ColumnInfo(name = "sellData") var sellData: String,
    @ColumnInfo(name = "sellAmount") var sellAmount: Double,
    @ColumnInfo(name = "returnedProductData") var returnedProductData: String,
    @ColumnInfo(name = "returnedAmount") var returnedAmount: Double,
    @ColumnInfo(name = "payment") var payment: Double

)