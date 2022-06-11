package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import com.mobile.liderstoreagent.data.models.returnedmodel.ReturnedData
import com.mobile.liderstoreagent.domain.usecases.ClientProductsUseCase
import com.mobile.liderstoreagent.domain.usecases.ReturnedProductUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.ClientProductUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.ReturnedProductUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class ClientProductsViewModel : ViewModel() {

    private val productsUseCase: ClientProductsUseCase = ClientProductUseCaseImpl()
    val errorProductsLiveData: LiveData<Unit> = productsUseCase.errorClientProductsLiveData
    val progressProductsLiveData = MutableLiveData<Boolean>()
    val connectionErrorProductsLiveData = MutableLiveData<Unit>()
    val successProductsLiveData = MediatorLiveData<List<ClientProducts>>()


    private val returnedUseCase: ReturnedProductUseCase = ReturnedProductUseCaseImpl()
    val errorReturnedLiveData: LiveData<Unit> = returnedUseCase.errorReturnedLiveData
    val errorTimeOutLive: LiveData<Unit> = returnedUseCase.errorTimeOutLiveData
    val errorReturnedResponeLiveData : LiveData<String> = returnedUseCase.errorResponseLiveData
    val progressReturnedLiveData = MutableLiveData<Boolean>()
    val connectionErrorReturnedLiveData = MutableLiveData<Unit>()
    val successReturnedLiveData = MediatorLiveData<Any>()


    fun returnedProduct(returnedData: ReturnedData) {
        if (isConnected()) {
            progressReturnedLiveData.value = true
            val liveData = returnedUseCase.returnedProduct(returnedData)
            successReturnedLiveData.addSource(liveData) {
                progressReturnedLiveData.value = false
                successReturnedLiveData.value = it
                successReturnedLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorReturnedLiveData.value = Unit
        }
    }


    fun getClientProducts(clientId: Int) {
        if (isConnected()) {
            progressProductsLiveData.value = true
            val liveData = productsUseCase.getClientProducts(clientId.toString())
            successProductsLiveData.addSource(liveData) {
                progressProductsLiveData.value = false
                successProductsLiveData.value = it
                successProductsLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorProductsLiveData.value = Unit
        }
    }

    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch(){
        closeLiveData.value = Unit
    }
}

