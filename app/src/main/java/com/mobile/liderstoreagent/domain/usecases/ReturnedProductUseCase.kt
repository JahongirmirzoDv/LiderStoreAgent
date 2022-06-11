package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.returnedmodel.ReturnedData

interface ReturnedProductUseCase {
    val errorReturnedLiveData: LiveData<Unit>
    val errorResponseLiveData: LiveData<String>
    val errorTimeOutLiveData: LiveData<Unit>
    fun returnedProduct(returnedData: ReturnedData): LiveData<Any>
}