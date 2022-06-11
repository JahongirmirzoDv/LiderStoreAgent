package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GetAgentExpenseList {

    @GET
    suspend fun getAgentExpenseList(
        @Url url: String
    ): Response<AgentExpenseGet>
}