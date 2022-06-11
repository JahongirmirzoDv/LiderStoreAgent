package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct

interface ProductWarehouseUseCase {
    val errorProductsLiveData: LiveData<Unit>
    fun getProducts(): LiveData<OwnProduct>
}
