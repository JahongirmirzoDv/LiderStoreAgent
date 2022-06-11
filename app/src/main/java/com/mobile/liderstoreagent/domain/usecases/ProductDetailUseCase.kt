package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail

interface ProductDetailUseCase {
    val errorProductLiveData: LiveData<Unit>
    fun getProduct(productId: String): LiveData<ProductDetail>
}