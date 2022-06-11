package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.returnedmodel.ReturnedData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReturnedProductApi {
    @POST("order/returned-product/")
    suspend fun returnedProduct(
        @Header("Accept") app_json: String,
        @Body returnedData: ReturnedData,
    ): Response<Any>
}