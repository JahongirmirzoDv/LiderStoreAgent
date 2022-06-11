package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

/**
 * Ombordagi maxsulotlar va o`zidagi maxsulotlardan foydalib sotish bo`limi shu yerda api
 * */


interface WarehouseApi {

    @GET("warehouse/warehouses/")
    suspend fun getWarehouses(): Response<WarehouseData>

    @GET
    suspend fun getOwnCategories(
        @Url url: String,
        @Header("Authorization") token: String
    ): Response<AgentData>

}