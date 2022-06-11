package com.mobile.liderstoreagent.data.models.warehouse

data class WarehouseData(
    val count :Int,
    val results:List<Warehouse>
){
    class Warehouse(
        val id: Int,
        val name: String,
        val address: String,
        val categories:List<CategoryModel>
    ){
        class CategoryModel(
            val category__name:String,
            val category__id:Int
        )
    }
}