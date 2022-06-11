package com.mobile.liderstoreagent.data.models.expense

data class AgentExpensePost(
    val approved: Boolean,
    val category: String,
    val description: String,
    val name: String,
    val quantity: Double,
    val user: Int,
    val expense_sub_category:Int
)