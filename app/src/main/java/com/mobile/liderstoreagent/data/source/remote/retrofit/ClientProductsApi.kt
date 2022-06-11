package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ClientProductsApi {
    @GET
    suspend fun getClientProducts(
            @Url url: String,
            //  @Header("Authorization") token: String
    ): Response<List<ClientProducts>>
}