package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ReportHistoryApi {
    @GET
    suspend fun getHistory(
        @Url url: String,
        @Query("client_id") clientId:String
        //  @Header("Authorization") token: String
    ): Response<List<ReportHistory>>

}