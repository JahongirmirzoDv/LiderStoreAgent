package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.PriceTypeApi
import com.mobile.liderstoreagent.domain.repositories.repo.PriceTypeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PriceTypeRepoImpl : PriceTypeRepo {

    private val api = ApiClient.retrofit.create(PriceTypeApi::class.java)
    override suspend fun getPriceType(): Flow<Result<PriceType?>> = flow {
        try {
            val response = api.getPriceType()
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }

        } catch (e: Exception) {
        }
    }
}