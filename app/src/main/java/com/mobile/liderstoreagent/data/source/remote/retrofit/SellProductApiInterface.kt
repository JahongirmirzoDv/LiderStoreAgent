package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.sellmodel.SellProductResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SellProductApiInterface {
    @FormUrlEncoded
    @POST("order/sell-order-list/")
    suspend fun sellProduct(
        @Header("Accept") app_json: String,
        @Field("price") price: String,
        @Field("quantity") quantity: String,
        @Field("client") client: Int,
        @Field("warehouse") warehouse: Int,
        @Field("warehouse_name") warehouseName: String,
        @Field("product") product: Int
    ): Response<SellProductResponse>
}

