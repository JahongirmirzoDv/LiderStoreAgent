package com.mobile.liderstoreagent.data.source.remote.retrofit

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PaymentApi {
    @Multipart
    @POST("order/sell-order-payment/")
    suspend fun payPayment(
        @Header("Accept") app_json: String,
        @Part("client") client: Int,
        @Part("payment_type") address: RequestBody,
        @Part("payment") payment: Double,
        @Part("client_products") productId: Int?,
        @Part("comments") comment: String
    ): Response<Any>
}