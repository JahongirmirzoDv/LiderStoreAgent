package com.mobile.liderstoreagent.data.models.getproduct

data class Product(
    val category: Category,
    val code: String,
    val estimated_delivery_days: Int,
    val id: Int,
    val image:String?,
    val info:String?,
    val name: String,
    val product_type: String,
    val provider: Provider,
    val quantity: Double,
    val unit: String
)