package com.mobile.liderstoreagent.data.models.historymodel

data class History(
    val id: Int,
    val quantity: String,
    val given_quantity: String,
    val status: String,
    val discount: String,
    val product_discount: String,
    val created_date: String,
    val updated_date: String,
    val by_agent: Boolean,
    val warehouse: Int,
    val client: Int,
    val product: Int,
    val price: String,
)