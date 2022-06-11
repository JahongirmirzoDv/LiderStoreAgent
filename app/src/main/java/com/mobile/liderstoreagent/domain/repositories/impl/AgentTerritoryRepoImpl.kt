package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.clientmodel.Territory
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.AgentTerritoryApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.domain.repositories.repo.AgentTerritoryRepo
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AgentTerritoryRepoImpl : AgentTerritoryRepo {

    private val api = ApiClient.retrofit.create(AgentTerritoryApi::class.java)
    override suspend fun getTerritories(): Flow<Result<List<Territory>?>> = flow {
        try {
            val response =
                api.getAgentTerritories("client/get-agent-all-territories/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }

        } catch (e: Exception) {
            //   emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xato!")
        }
    }
}