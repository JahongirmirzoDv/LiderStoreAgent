package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.data.models.returnedmodel.ReturnedData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ReturnedProductApi
import com.mobile.liderstoreagent.domain.repositories.repo.ReturnedProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReturnedProductRepoImpl : ReturnedProductRepository {
    override val errorTimeOutData = MutableLiveData<Unit>()
    private val api = ApiClient.retrofit.create(ReturnedProductApi::class.java)
    override suspend fun returnedProduct(returnedData: ReturnedData): Flow<Result<Pair<Int, Any?>>> =
        flow {
            try {
                val response = api.returnedProduct(
                    "application/json",
                    returnedData
                )
                if (response.code() == 200) {
                    emit(Result.success(Pair(200, response.body())))
                } else if (response.code() == 404) {
                    emit(Result.success(Pair(404, null)))
                }
            } catch (e: Exception) {
                errorTimeOutData.postValue(Unit)
            }
        }
}