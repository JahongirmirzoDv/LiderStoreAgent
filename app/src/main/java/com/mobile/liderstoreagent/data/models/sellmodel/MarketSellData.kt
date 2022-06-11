package com.mobile.liderstoreagent.data.models.sellmodel

class MarketSellData(
    val quantity:Double,
    val client:Int,
    val product:Int,
    val price:Int,
    val discount :Double,
    val warehouse:Int,
    val status:String,
    val agent_sell_price:Double,
    val product_discount:Double
)