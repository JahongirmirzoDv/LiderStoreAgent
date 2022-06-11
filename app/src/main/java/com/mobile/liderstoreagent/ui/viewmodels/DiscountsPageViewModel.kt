package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import com.mobile.liderstoreagent.domain.usecases.DiscountedProductsUseCase
import com.mobile.liderstoreagent.domain.usecases.DiscountsUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.DiscountUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.DiscountedProductsUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class DiscountsPageViewModel : ViewModel() {

    private val discountsUseCase: DiscountsUseCase = DiscountUseCaseImpl()
    val errorDiscountsLiveData: LiveData<Unit> = discountsUseCase.errorDiscountsLiveData
    val progressDiscountsLiveData = MutableLiveData<Boolean>()
    val connectionErrorDiscountsLiveData = MutableLiveData<Unit>()
    val successDiscountsLiveData = MediatorLiveData<List<Discounts>>()

    private val discountedProductsUseCase: DiscountedProductsUseCase =
        DiscountedProductsUseCaseImpl()
    val errorDiscountedProductsLiveData: LiveData<Unit> =
        discountedProductsUseCase.errorDiscountedProductsLiveData
    val progressDiscountedProductsLiveData = MutableLiveData<Boolean>()
    val connectionErrorDiscountedProductsLiveData = MutableLiveData<Unit>()
    val successDiscountedProductsLiveData = MediatorLiveData<List<DiscountedProduct>>()


    init {
        getDiscounts()
        //getDiscountedProducts(1)
    }


    fun getDiscounts() {
        if (isConnected()) {
            progressDiscountsLiveData.value = true
            val liveData = discountsUseCase.getDiscounts()
            successDiscountsLiveData.addSource(liveData) {
                progressDiscountsLiveData.value = false
                successDiscountsLiveData.value = it
                successDiscountsLiveData.removeSource(liveData)
            }
        } else {
            progressDiscountsLiveData.value = false
            connectionErrorDiscountsLiveData.value = Unit
        }

    }

    fun getDiscountedProducts(discountId: Int) {
        if (isConnected()) {
            progressDiscountedProductsLiveData.value = true
            val liveData = discountedProductsUseCase.getDiscountedProducts(discountId.toString())
            successDiscountedProductsLiveData.addSource(liveData) {
                progressDiscountedProductsLiveData.value = false
                successDiscountedProductsLiveData.value = it
                successDiscountedProductsLiveData.removeSource(liveData)
            }
        } else {
            progressDiscountedProductsLiveData.value = false
            connectionErrorDiscountedProductsLiveData.value = Unit
        }
    }
}