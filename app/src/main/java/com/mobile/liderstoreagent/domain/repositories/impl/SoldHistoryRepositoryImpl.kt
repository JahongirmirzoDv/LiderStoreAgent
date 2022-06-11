package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.SoldHistoryApi
import com.mobile.liderstoreagent.domain.repositories.repo.SoldHistoryRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SoldHistoryRepositoryImpl : SoldHistoryRepository {
    private val api = ApiClient.retrofit.create(SoldHistoryApi::class.java)
    override suspend fun soldHistory(): Flow<Result<List<SoldProductHistory>?>> = flow {
        try {
            val response = api.soldHistory("order/agent-order-list/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }


}