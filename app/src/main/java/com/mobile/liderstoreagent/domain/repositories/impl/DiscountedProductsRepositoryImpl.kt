package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.DiscountedProductsApi
import com.mobile.liderstoreagent.domain.repositories.repo.DiscountedProductsRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DiscountedProductsRepositoryImpl : DiscountedProductsRepository {
    private val api = ApiClient.retrofit.create(DiscountedProductsApi::class.java)
    override suspend fun getDiscountedProducts(discountId: String): Flow<Result<List<DiscountedProduct>?>> = flow {

        try {
            val response = api.getDiscountedProducts("expense_discount/discount-products/${discountId}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }


}