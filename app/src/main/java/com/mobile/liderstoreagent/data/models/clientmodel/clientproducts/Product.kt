package com.mobile.liderstoreagent.data.models.clientmodel.clientproducts

data class Product(
    val category: Int,
    val code: String,
    val estimated_delivery_days: Int,
    val id: Int,
    val name: String,
    val product_type: String,
    val provider: Int,
    val unit: String
)