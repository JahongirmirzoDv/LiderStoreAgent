package com.mobile.liderstoreagent.data.source.remote.retrofit


import com.mobile.liderstoreagent.data.models.loginmodel.LoginData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApiInterface {

    @POST("user/login/")
    suspend fun userLogin(
        @Header("Accept") app_json: String,
        @Body loginData: LoginData,
    ): Response<LoginResponse>

}