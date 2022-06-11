package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductDetailApi {
    @GET
    suspend fun getProduct(
        @Url url: String
    ): Response<ProductDetail>
}