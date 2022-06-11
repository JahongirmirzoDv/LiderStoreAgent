package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.historymodel.History
import com.mobile.liderstoreagent.data.models.historymodel.HistoryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface ProductListApi {

    @GET
    suspend fun getProducts(
        @Url url: String,
        @Header("Authorization") token: String,
        @Query("page")
        pageNumber: Int = 10,
        @Query("offset")
        offset:Int?=0
    ): Response<HistoryData<History>>


}