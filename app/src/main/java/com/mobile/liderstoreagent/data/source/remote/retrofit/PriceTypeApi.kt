package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType
import retrofit2.Response
import retrofit2.http.GET

interface PriceTypeApi {
    @GET("client/price-type-list/")
    suspend fun getPriceType(): Response<PriceType>
}