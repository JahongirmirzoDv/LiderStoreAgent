package com.mobile.liderstoreagent.data.models

data class SellToClientOwnProduct(
    var client: Int,
    var price: String,
    var name: String,
    var product: Int,
    var quantity: String,
    var quantityAll: String
)