package com.mobile.liderstoreagent.ui.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ProductWarehouseApi
import com.mobile.liderstoreagent.domain.repositories.repo.ProductWarehouseRepository

class OwnProductViewModel(
    private val context: Context
) : ViewModel() {

    private val repository = ProductWarehouseRepository(ApiClient.retrofit.create(ProductWarehouseApi::class.java))

    fun getPro(str: String) = repository.getProducts(context, str).cachedIn(viewModelScope)

//    private val productUseCase: ProductWarehouseUseCase = ProductWarehouseUseCaseImpl()
//    val errorProductLiveData: LiveData<Unit> = productUseCase.errorProductsLiveData
//    val progressProductLiveData = MutableLiveData<Boolean>()
//    val connectionErrorProductLiveData = MutableLiveData<Unit>()
//    val successProductLiveData = MediatorLiveData<OwnProduct>()
//
//    fun getProduct() {
//        if (isConnected()) {
//            progressProductLiveData.value = true
//            val liveData = productUseCase.getProducts()
//            successProductLiveData.addSource(liveData) {
//                progressProductLiveData.value = false
//                successProductLiveData.value = it
//                successProductLiveData.removeSource(liveData)
//            }
//        } else {
//            connectionErrorProductLiveData.value = Unit
//        }
//    }

}

class OwnViewModelFactory(
    private val context: Context,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OwnProductViewModel::class.java)) {
            return OwnProductViewModel(context) as T
        }
        throw IllegalArgumentException("$modelClass is not FurnitureViewModel")
    }
}