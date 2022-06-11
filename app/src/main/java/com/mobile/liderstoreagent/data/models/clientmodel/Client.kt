package com.mobile.liderstoreagent.data.models.clientmodel

data class Client(
    val address: String,
    val id: Int,
    val name: String,
    val work_phone_number: String,
    val responsible_agent: String,
    val market_code :Int
)