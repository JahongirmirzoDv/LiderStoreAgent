package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ClientsApiInterface {

    @GET//("report/client-debt-list/")
    suspend fun getClientList(
        @Url url: String,
        @Query("car_name") filter:String
    ): Response<List<ClientsData>>
}