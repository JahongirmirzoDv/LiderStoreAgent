package com.mobile.liderstoreagent.data.models.sellmodel

data class SellProductData(
    val price: String,
    val quantity: String,
    val status: String = "",
    val deadline: String = "",
    val client: Int,
    val warehouse:Int,
    val warehouse_name:String,
    val product: Int
)