package com.mobile.liderstoreagent.data.source.remote.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClientReportApiInterface {

    @Multipart
    @POST("warehouse/agent-report/")
    suspend fun sendClientReport(
        @Header("Accept") app_json: String,
        @Part("comment") comment: RequestBody,
        @Part images: List<MultipartBody.Part?>,
        @Part("client") clientId: Int,
        @Part("agent") agentId: Int
    ): Response<Any>

}