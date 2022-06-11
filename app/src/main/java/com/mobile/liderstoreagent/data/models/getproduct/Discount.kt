package com.mobile.liderstoreagent.data.models.getproduct

data class Discount(
    val active: Boolean,
    val created_date: String,
    val deadline: String,
    val discount: Int,
    val id: Int,
    val name: String,
    var isChecked :Boolean = false
)