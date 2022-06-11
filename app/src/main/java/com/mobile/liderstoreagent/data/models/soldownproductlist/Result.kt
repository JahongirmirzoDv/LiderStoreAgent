package com.mobile.liderstoreagent.data.models.soldownproductlist

data class Result(
    val approved: Boolean,
    val client: String,
    val created_date: String,
    val id: Int,
    val last_returnded_quantity: String,
    val price: String,
    val product: String,
    val quantity: String,
    val returned_quantity: String,
    val status: String,
    val updated_date: String
)