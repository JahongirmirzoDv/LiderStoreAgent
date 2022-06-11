package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface ProductsApiInterface {
    @GET
    suspend fun getProducts(
        @Url url: String,
        @Query("code") code: String,
        @Query("name") name: String
    ): Response<List<ProductData>>

    @GET
    suspend fun getOwnProducts(
        @Url url: String,
          @Header("Authorization") token: String
    ): Response<OwnProduct>

}