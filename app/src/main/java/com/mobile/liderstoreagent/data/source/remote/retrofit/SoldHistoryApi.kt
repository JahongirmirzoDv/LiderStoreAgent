package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.mobile.liderstoreagent.data.models.soldownproductlist.SoldProductListOwn
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface SoldHistoryApi {
    @GET
    suspend fun soldHistory(
        @Url url: String,
        //  @Header("Authorization") token: String
    ): Response<List<SoldProductHistory>>

    @GET("order/agent-to-client-sell-order/")
    suspend fun soldProducts(
        @Header("Authorization") token: String,
        @Query("page")
        pageNumber: Int = 10,
        @Query("offset")
        offset: Int? = 10,
        @Query("search") std: String,
        @Query("date-from") from: String,
        @Query("date-to") to: String
    ): Response<SoldProductListOwn>
}