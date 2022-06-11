package com.mobile.liderstoreagent.domain.repositories.impl

import android.util.Log
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductData
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductResponse
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.SellProductApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.SellProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SellProductRepositoryImpl : SellProductRepository {
    private val api = ApiClient.retrofit.create(SellProductApiInterface::class.java)

    override suspend fun sellProduct(productData: SellProductData):
            Flow<Result<Pair<Int, SellProductResponse?>>> = flow {
//        try {
//            val response = api.sellProduct(
//                "application/json",
//                productData.price,
//                productData.quantity,
//                productData.client,
//                productData.warehouse,
//                productData.product
//            )
//            if (response.code() == 201) {
//                emit(Result.success(Pair(201, response.body())))
//            } else if (response.code() == 400) {
//                emit(Result.success(Pair(400, null)))
//            }
//        } catch (e: Exception) {
//            Log.d("SELL", "exception = $e")
//        }

        try {
            val response = api.sellProduct(
                "application/json",
                productData.price,
                productData.quantity,
                productData.client,
                productData.warehouse,
                productData.warehouse_name,
                productData.product
            )
            if (response.code() == 201) {
                emit(Result.success(Pair(201, response.body()))) as Unit
            } else if (response.code() == 400) {
                emit(Result.success(Pair(400, null)))
            }

        } catch (e: Exception) {
            Log.d("SELL", "exception = $e")
        }
    }
}