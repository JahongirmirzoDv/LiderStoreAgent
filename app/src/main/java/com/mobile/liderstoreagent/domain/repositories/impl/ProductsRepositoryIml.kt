package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ProductsApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.ProductsRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsRepositoryIml : ProductsRepository {
    private val api = ApiClient.retrofit.create(ProductsApiInterface::class.java)
    override suspend fun getProducts(categoryId: String,clientId:String,code:String,name:String): Flow<Result<List<ProductData>?>> = flow {

        try {
            val response = api.getProducts("product/category-agent-product/${categoryId}/${clientId}/",code, name)
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }

    override suspend fun getOwnProducts(
        categoryId: String,
        clientId: String,
    ): Flow<Result<OwnProduct>>  = flow {

        try {
            val response = api.getOwnProducts("product/agent-product-list/?category_id=$categoryId",
                "token ${TokenSaver.token}"
            )
            if (response.code() == 200) {
                emit(Result.success(response.body()!!))
                log(response.body().toString(), "QQQ")
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xatolik!")
        }

    }

}