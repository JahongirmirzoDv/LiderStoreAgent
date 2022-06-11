package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ProductDetailApi
import com.mobile.liderstoreagent.domain.repositories.repo.ProductDetailRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductDetailRepositoryImpl : ProductDetailRepository {
    private val api = ApiClient.retrofit.create(ProductDetailApi::class.java)
    override suspend fun productFullData(productId: String): Flow<Result<ProductDetail?>> = flow {

        try {
            val response = api.getProduct("product/product-detail/${productId}/")
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