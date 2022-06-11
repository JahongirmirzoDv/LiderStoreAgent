package com.mobile.liderstoreagent.data.models.warehouse

data class AgentData(

    val id: Int,
    val first_name: String,
    val last_name: String,
    val categories: List<CategoryModel>

) {
    class CategoryModel(
        val category: String,
        val category_id: Int
    )

}