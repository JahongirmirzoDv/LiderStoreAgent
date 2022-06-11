package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.agentbox.BoxCount
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface BoxCountApi {

    @GET
    suspend fun getBoxCount(
        @Url url: String
    ): Response<BoxCount>
}