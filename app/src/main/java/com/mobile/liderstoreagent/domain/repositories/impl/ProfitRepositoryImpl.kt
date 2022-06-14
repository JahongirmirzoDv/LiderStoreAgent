package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.Profit
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ProfitApi
import com.mobile.liderstoreagent.domain.repositories.ProfitRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfitRepositoryImpl : ProfitRepository {
    private val api = ApiClient.retrofit.create(ProfitApi::class.java)

    override suspend fun getProfit(
        id: String,
        start_date: String,
        to_date: String,
    ): Flow<Result<Profit?>> = flow {
        try {
            val response = api.getProfit(id, start_date, to_date)
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }

        } catch (e: Exception) {
            //   emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xato!")
        }
    }
}