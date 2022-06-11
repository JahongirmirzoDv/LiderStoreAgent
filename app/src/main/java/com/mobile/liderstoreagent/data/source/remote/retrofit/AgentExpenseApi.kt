package com.mobile.liderstoreagent.data.source.remote.retrofit

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AgentExpenseApi {
    @Multipart
    @POST("expense_discount/expense-list/")
    suspend fun addExpense(
        @Header("Accept") app_json: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("quantity") quantity: Double,
        @Part("category") category : RequestBody,
        @Part("approved") approved: Boolean,
        @Part("user") user: Int,
        @Part("expense_sub_category") expense_category: Int
    ): Response<Any>
}