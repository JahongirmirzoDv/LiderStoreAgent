package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct

interface DiscountedProductsUseCase {
    val errorDiscountedProductsLiveData: LiveData<Unit>
    fun getDiscountedProducts(discountId: String): LiveData<List<DiscountedProduct>>
}