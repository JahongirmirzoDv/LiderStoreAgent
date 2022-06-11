package com.mobile.liderstoreagent.data.source.remote.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UpdateClientApi {
    @Multipart
    @PUT
          suspend fun updateClient(
        @Url url: String,
        @Part("id")id: Int,
        @Header("Accept") app_json: String,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: Double,
        @Part("longitude") longitude: Double,
        @Part("director_phone_number") phone_number1: RequestBody,
        @Part("work_phone_number") phone_number2: RequestBody,
        @Part("INN") INN: RequestBody,
        @Part("director") director: RequestBody,
        @Part("birthdate") birthdate: RequestBody,
        @Part("responsible_agent") responsible_person: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("sale_agent") agentId: Int,
        @Part("car") car: Int,
        @Part("target") target: RequestBody,
        @Part("market_type") marketType: Int,
        @Part("territory") territory: Int,
        @Part("price_type") priceType: Int,
    ): Response<Any>

}