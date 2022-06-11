package com.mobile.liderstoreagent.data.models.returnedmodel

class ReturnedData(
    val returned_quantity : Double,
    val status: String,
    val sell_order: Int,
    val agent: Int,
    val client: Int,
    val is_approved:String,
    val product :Int
)