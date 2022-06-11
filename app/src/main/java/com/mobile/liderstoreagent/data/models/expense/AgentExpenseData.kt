package com.mobile.liderstoreagent.data.models.expense

data class AgentExpenseData(
    val approved: Boolean,
    val category: String,
    val expense_category:Int,
    val created_date: String,
    val description: String,
    val first_name: String,
    val last_name: String,
    val quantity:String,
    val name: String,
    val phone_number: String,
    val updated_date: String,
    val user: Int
)