package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PlanDetailApiInterface {
    @GET
    suspend fun getPlanDetails(
        @Url url: String
        //  @Header("Authorization") token: String
    ): Response<List<PlanDetail>>

}