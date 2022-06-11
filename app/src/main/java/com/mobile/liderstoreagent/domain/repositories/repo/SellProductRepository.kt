package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.sellmodel.SellProductData
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductResponse
import kotlinx.coroutines.flow.Flow

interface SellProductRepository {
    suspend fun sellProduct(productData: SellProductData)
    : Flow<Result<Pair<Int,SellProductResponse?>>>
}