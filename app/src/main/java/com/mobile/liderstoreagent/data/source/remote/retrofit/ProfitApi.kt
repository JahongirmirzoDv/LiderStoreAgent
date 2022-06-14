package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.Profit
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfitApi {
    @GET("report/agent-profit-statistics/{id}/")
    suspend fun getProfit(
        @Path("id") id: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
    ): Response<Profit>
}