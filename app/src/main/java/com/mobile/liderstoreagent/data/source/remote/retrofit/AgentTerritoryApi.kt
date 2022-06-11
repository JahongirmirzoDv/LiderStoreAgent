package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.clientmodel.Territory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AgentTerritoryApi {

    @GET
    suspend fun getAgentTerritories(
        @Url url: String,
    ): Response<List<Territory>>

}