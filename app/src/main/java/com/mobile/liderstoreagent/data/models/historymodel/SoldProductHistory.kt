package com.mobile.liderstoreagent.data.models.historymodel

data class SoldProductHistory(
    val client: String,
    val created_date: String,
    val price: String,
    val product_name: String,
    val quantity: String,
    val status: String
)