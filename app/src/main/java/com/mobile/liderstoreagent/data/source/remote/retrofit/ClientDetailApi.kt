package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ClientDetailApi {

    @GET
    suspend fun getClientDetail(
        @Url url: String,
    ): Response<ClientDetail>

}