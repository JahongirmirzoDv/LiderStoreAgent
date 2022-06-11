package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AddClientSelectorsApi {
    @GET
    suspend fun getClientSelectors(
        @Url url: String,
        //  @Header("Authorization") token: String
    ): Response<AddClientSelectors>
}