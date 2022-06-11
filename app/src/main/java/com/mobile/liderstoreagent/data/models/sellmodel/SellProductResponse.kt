package com.mobile.liderstoreagent.data.models.sellmodel

data class SellProductResponse(
    val client: Int,
    val deadline: Any,
    val id: Int,
    val price: String,
    val product: Int,
    val quantity: String,
    val warehouse: Int,
    val warehouseName: String,
    val status: String
)