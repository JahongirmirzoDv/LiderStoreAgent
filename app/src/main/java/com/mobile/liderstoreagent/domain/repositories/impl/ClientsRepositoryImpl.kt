package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ClientsApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.ClientsRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClientsRepositoryImpl : ClientsRepository {
    private val api = ApiClient.retrofit.create(ClientsApiInterface::class.java)
    override suspend fun getClients(filter:String): Flow<Result<Pair<Int,List<ClientsData>?>>> = flow {
        try {
            val response = api.getClientList("report/client-debt-list/${TokenSaver.getAgentId()}/",filter)
            if (response.code() == 200) {
                emit(Result.success(Pair(200,response.body())))
                log(response.body().toString(), "QQQ")
            }
            else if(response.code() == 500){
                emit(Result.success(Pair(500,null)))
            }

        } catch (e: Exception) {
            log("TTTD", "exception = $e" + "Xatolik!")
        }
    }

}