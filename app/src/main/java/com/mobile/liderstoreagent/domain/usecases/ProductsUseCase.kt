package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct

interface ProductsUseCase {
    val errorProductsLiveData: LiveData<Unit>
    val errorOwnProductsLiveData: LiveData<Unit>
    fun getProducts(categoryId: String,clientId:String,code:String,name:String): LiveData<List<ProductData>>
    fun getOwnProducts(categoryId: String,clientId:String): LiveData<OwnProduct>
}