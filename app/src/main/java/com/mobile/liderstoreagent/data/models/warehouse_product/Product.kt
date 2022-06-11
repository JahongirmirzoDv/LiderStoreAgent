package com.mobile.liderstoreagent.data.models.warehouse_product

data class Product(
    val category: Category,
    val created_date: String,
    val date_of_manufacture: Any,
    val estimated_delivery_days: Int,
    val id: Int,
    val image: Any,
    val incoming_price: String,
    val incoming_price_is_usd: Boolean,
    val info: Any,
    val last_price: String,
    val name: String,
    val product_type: String,
    val profit: String,
    val provider: Provider,
    val quantity: String,
    val sell_by: Any,
    val unit: String,
    val updated_date: String,
    val warehouse: Int
)