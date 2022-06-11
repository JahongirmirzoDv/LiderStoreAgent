package com.mobile.liderstoreagent.data.models.warehouse_product

data class Category(
    val department: Department,
    val discount: Int,
    val id: Int,
    val name: String
)