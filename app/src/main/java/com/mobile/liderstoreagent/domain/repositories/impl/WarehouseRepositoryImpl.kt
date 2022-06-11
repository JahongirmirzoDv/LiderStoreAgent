package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.WarehouseApi
import com.mobile.liderstoreagent.domain.repositories.repo.WarehouseRepository
import com.mobile.liderstoreagent.utils.Constants
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WarehouseRepositoryImpl : WarehouseRepository {

    private val api = ApiClient.retrofit.create(WarehouseApi::class.java)

    override suspend fun getWarehouses():
            Flow<Result<WarehouseData?>> = flow {
        try {
            val response = api.getWarehouses()
            if (response.code() == 200){
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }

    override suspend fun getOwnCategories(): Flow<Result<AgentData?>> = flow{


        try {
            val response = api.getOwnCategories("${Constants.BASE_URL}user/agent-detail/${TokenSaver.getAgentId()}",
                TokenSaver.token
            )
            if (response.code() == 200){
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }

    }

}