package com.mobile.liderstoreagent.data.models.historymodel.other

data class Result(
    val agent: Agent,
    val approved: Boolean,
    val created_date: String,
    val id: Int,
    val last_returnded_quantity: String,
    val price: Int,
    val product: Product,
    val quantity: String,
    val returned_quantity: String,
    val status: String,
    val updated_date: String
)