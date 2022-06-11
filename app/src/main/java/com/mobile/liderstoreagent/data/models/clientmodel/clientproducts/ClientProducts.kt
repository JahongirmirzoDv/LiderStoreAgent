package com.mobile.liderstoreagent.data.models.clientmodel.clientproducts

data class ClientProducts(
    val created_date: String,
    val given_quantity: Any,
    val id: Int,
    val price: Price,
    val product: Product,
    val quantity: String,
    val status: String,
    val updated_date: String
)