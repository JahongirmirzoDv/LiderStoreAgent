package com.mobile.liderstoreagent.data.source.local.clients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "marketName") var marketName: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "responsiblePerson") var responsiblePerson: String,
    @ColumnInfo(name = "directorPhone") var directorPhone: String,
    @ColumnInfo(name = "workPhone") var workPhone: String,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "agentId") var agentId: Int,
    @ColumnInfo(name = "INN") var INN: String,
    @ColumnInfo(name = "directorName") var directorName: String,
    @ColumnInfo(name = "birthDate") var birthDate: String,
    @ColumnInfo(name = "car") var car: Int,
    @ColumnInfo(name = "market") var market: Int,
    @ColumnInfo(name = "target") var target: String,
    @ColumnInfo(name = "territory") var territory: Int,
    @ColumnInfo(name = "price_type") var price_type: Int,
)
