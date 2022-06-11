package com.mobile.liderstoreagent.data.models.warehouse_product

data class ProductSm(
    val id: Int,
    val quantity: String,
    val returned_quantity: String,
    val status: String,
    val approved: Boolean,
    val agent: Agent,
    val product: Product,
    val category: Category,
    val provider: Provider,
) {

    class Agent(
        val id: Int,
        val department: Boolean,
        val phone_number: String,
        val role: String,
        val first_name: String,
        val last_name: String,
        val self_birth_date: String,
        val self_address: String,
        val family_address: String,
    )

    class Product(
        val id: Int,
        val name: String,
        val image: String,
        val info: String,
        val unit: String,
        val product_type: String,
        val estimated_delivery_days: Int,
        val quantity: String,
        val last_price: String,
        val profit: String,
        val incoming_price: String,
        val warehouse: Int,
        val created_date:String
    )

    class Category(
        val id: Int,
        val discount: Int,
        val name: String,
        department: Department,
    ) {
        class Department(
            val id: Int,
            val name: String,
        )
    }

    class Provider(
        val id: Int,
        val name: String,
        val address: String,
        val phone_number1: String,
        val phone_number2: String,
        val account_number: String,
        val bank: String,
        val bank_code: String,
        val INN: String,
        val director: String,
        val MFO: String,
        val NDS: String,
        val responsible_agent: String,
    )

}