package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductData
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductResponse

interface SellProductUseCase {
    val errorNotResponseLiveData : LiveData<String>
    val errorResponseLiveData : LiveData<String>


    fun sellProduct(productData: SellProductData) : LiveData<SellProductResponse>
}