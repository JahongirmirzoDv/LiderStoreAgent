package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.HistoryClientApi
import com.mobile.liderstoreagent.domain.repositories.repo.HistoryRepository
import com.mobile.liderstoreagent.utils.Constants
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl:HistoryRepository {
    private val api = ApiClient.retrofit.create(HistoryClientApi::class.java)

    override suspend fun setProducts(
        id: String,
        data: ToAgentSellOrder,
    ): Flow<Result<ToAgentSellOrder?>> = flow {
        try {
            val response = api.setProduct("${Constants.BASE_URL}${id}",
                "token ${TokenSaver.token}",
                data
//                id
            )
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }

    override suspend fun setProducts1(
        id: String,
        data: ToAgentSellOrder,
    ): Flow<Result<ToAgentSellOrder?>> = flow {
        try {
            val response = api.setProduct1("${Constants.BASE_URL}${id}",
                "token ${TokenSaver.token}",
                data
//                id
            )
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