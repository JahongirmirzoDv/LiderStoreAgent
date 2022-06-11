package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    suspend fun productFullData(id:String): Flow<Result<ProductDetail?>>
}