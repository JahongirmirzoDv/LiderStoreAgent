package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.data.models.historymodel.other.AllGetProduct
import retrofit2.Response
import retrofit2.http.*

interface HistoryClientApi {

    @GET
    suspend fun getHistory(
        @Url url: String,
        @Header("Authorization") token: String,
        @Query("page")
        pageNumber: Int = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query ("search") std: String
    ): Response<AllGetProduct>

    @PATCH
    suspend fun setProduct(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body data: ToAgentSellOrder
//        @Body id: String,
    ): Response<ToAgentSellOrder>

    @PATCH
    suspend fun setProduct1(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body data: ToAgentSellOrder
//        @Body id: String,
    ): Response<ToAgentSellOrder>

}