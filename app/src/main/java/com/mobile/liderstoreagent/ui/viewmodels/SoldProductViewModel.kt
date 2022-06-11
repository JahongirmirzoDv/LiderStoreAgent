package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.SoldHistoryApi
import com.mobile.liderstoreagent.domain.repositories.SoldProductRepository
import com.mobile.liderstoreagent.utils.NetworkHelper

class SoldProductViewModel(val networkHelper: NetworkHelper): ViewModel() {

    private val repository = SoldProductRepository(ApiClient.retrofit.create(SoldHistoryApi::class.java))

    fun getSoldList(str: String) = repository.getSoldProducts(str)


}