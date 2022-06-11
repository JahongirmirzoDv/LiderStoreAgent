package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mobile.liderstoreagent.app.App
import com.mobile.liderstoreagent.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object ApiClient {

    //https://ferzu-warehouse.herokuapp.com/api/
    //http://159.65.233.187:8000/api/
    //http://vallisbackend.backoffice.uz/api/order/sell-order-list/
    //http://feruzbackend.backoffice.uz/admin/
    //http://sururdis.backoffice.uz
    //https://feruzbackend.backoffice.uz/

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(getHttpClientImage())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @JvmStatic
    private fun getHttpClientImage(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addLogging()
//        httpClient.addInterceptor(ChuckerInterceptor.Builder(App.instance).build())
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.writeTimeout(1, TimeUnit.MINUTES)
        return httpClient.build()
    }
}

fun OkHttpClient.Builder.addLogging(): OkHttpClient.Builder {
    val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Timber.e(message)
        }
    })

    logging.level = HttpLoggingInterceptor.Level.HEADERS
    addNetworkInterceptor(logging)
    addInterceptor(ChuckerInterceptor.Builder(App.instance).build())
    return this
}


