package com.mobile.liderstoreagent.data.models.payment

class PaymentData(
    val client: Int,
    val payment_type: String,
    val payment: Double,
    var productId: Int?,
    var comment:String
)