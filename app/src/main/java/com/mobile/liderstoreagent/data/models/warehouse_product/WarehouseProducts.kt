package com.mobile.liderstoreagent.data.models.warehouse_product

data class WarehouseProducts<T>(
    val count: Int,
    val results: ArrayList<T>,
)