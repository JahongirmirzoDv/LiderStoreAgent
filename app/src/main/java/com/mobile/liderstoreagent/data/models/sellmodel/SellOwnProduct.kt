package com.mobile.liderstoreagent.data.models.sellmodel

data class SellOwnProduct(
    val approved: Boolean = true,
    val client: Int,
    val last_returnded_quantity: String = "",
    val price: String,
    val product: Int,
    val quantity: String,
    val returned_quantity: String = "",
    val status: String = "delivered"
)