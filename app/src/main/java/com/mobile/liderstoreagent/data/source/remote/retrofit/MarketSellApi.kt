package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MarketSellApi {

    @POST("order/sell-order-list/")
    suspend fun marketSell(
        @Header("Accept") app_json: String,
        @Body marketSell: List<MarketSellData>,
    ): Response<Any>

    @POST("order/agent-to-client-sell-order/")
    suspend fun marketOwnSell(
        @Header("Accept") app_json: String,
        @Header("Authorization") token: String,
        @Body marketSell: List<SellToClientOwnProduct>,
    ): Response<Any>


}