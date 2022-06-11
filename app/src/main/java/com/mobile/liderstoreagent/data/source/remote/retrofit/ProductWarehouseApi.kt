package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import retrofit2.Response
import retrofit2.http.*

interface ProductWarehouseApi {

    @GET
    suspend fun getProduct(
        @Url url: String,
        @Header("Authorization") token: String,
        @Query("page")
        pageNumber: Int = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query("search") std: String ): Response<OwnProduct>
//
}