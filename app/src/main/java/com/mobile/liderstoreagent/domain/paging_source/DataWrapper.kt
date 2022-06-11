package com.mobile.liderstoreagent.domain.paging_source

/**
 *Created by farrukh_kh on 6/15/21 6:21 PM
 *uz.algorithmgateway.uymarket.data.entities
 **/
sealed class DataWrapper<T : Any> {
    class Empty<T : Any> : DataWrapper<T>()
    class Loading<T : Any> : DataWrapper<T>()
    data class Success<T : Any>(val data: T) : DataWrapper<T>()
    data class Error<T : Any>(val error: Exception) : DataWrapper<T>()
}