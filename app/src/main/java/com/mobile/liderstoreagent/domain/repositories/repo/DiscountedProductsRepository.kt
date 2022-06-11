package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import kotlinx.coroutines.flow.Flow

interface DiscountedProductsRepository {
    suspend fun getDiscountedProducts(discountId:String): Flow<Result<List<DiscountedProduct>?>>
}