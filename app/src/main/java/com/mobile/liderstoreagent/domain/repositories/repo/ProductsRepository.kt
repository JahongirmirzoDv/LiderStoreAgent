package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(categoryId:String,clientId:String,code:String,name:String): Flow<Result<List<ProductData>?>>
    suspend fun getOwnProducts(categoryId:String,clientId:String): Flow<Result<OwnProduct>>
}