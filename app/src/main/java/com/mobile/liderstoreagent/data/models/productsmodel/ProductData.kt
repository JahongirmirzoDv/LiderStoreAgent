package com.mobile.liderstoreagent.data.models.productsmodel

import java.io.Serializable

data class ProductData(
    val id: Int,
    val last_update: String,
    val name: String,
    val product_type: String,
    val provider: String,
    val quantity: String,
    val unit: String,
    val price:String,
    val price_id:Int,
    val error:String,
    val warehouse:Int,
    val warehouse_name:String,
    val code:String,
    val product_discount:String,
    val category_discount:String
) : Serializable