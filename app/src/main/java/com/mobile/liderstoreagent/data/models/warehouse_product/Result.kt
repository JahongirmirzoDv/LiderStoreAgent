package com.mobile.liderstoreagent.data.models.warehouse_product

import java.io.Serializable

data class Result(
    val agent: Int,
    val created_date: String,
    val id: Int,
    val incoming_price: String,
    val product: Product,
    var quantity: String,
    val updated_date: String
) : Serializable