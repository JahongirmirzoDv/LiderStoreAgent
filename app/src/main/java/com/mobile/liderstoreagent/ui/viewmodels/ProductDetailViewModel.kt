package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail
import com.mobile.liderstoreagent.domain.usecases.ProductDetailUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.ProductDetailUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected


class ProductDetailViewModel : ViewModel() {

    private val useCaseProduct: ProductDetailUseCase = ProductDetailUseCaseImpl()
    val errorProductLiveData: LiveData<Unit> = useCaseProduct.errorProductLiveData
    val progressProductLiveData = MutableLiveData<Boolean>()
    val connectionErrorProductLiveData = MutableLiveData<Unit>()
    val successProductLiveData = MediatorLiveData<ProductDetail>()

    fun getProduct(productId: String) {
        if (isConnected()) {
            progressProductLiveData.value = true
            val lvd = useCaseProduct.getProduct(productId)
            successProductLiveData.addSource(lvd) {
                progressProductLiveData.value = false
                successProductLiveData.value = it
                successProductLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorProductLiveData.value = Unit
        }
    }

}