package com.mobile.liderstoreagent.data.models.clientmodel.clientdetail

data class ClientDetail(
    val INN: String,
    val address: String,
    val birthdate: String,
    val car: Car,
    val created_date: String,
    val director: String,
    val director_phone_number: String,
    val id: Int,
    val image: String,
    val latitude: String,
    val longitude: String,
    val market_code: Int,
    val market_type: MarketType,
    val name: String,
    val price_type: PriceType,
    val responsible_agent: String,
    val sale_agent: SaleAgent,
    val sale_agents: Any,
    val target: String,
    val territory: Territory,
    val work_phone_number: String
)