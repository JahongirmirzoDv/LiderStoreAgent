package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ClientDetailApi
import com.mobile.liderstoreagent.domain.repositories.repo.ClientDetailRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClientDetailRepoImpl : ClientDetailRepo {

    private val api = ApiClient.retrofit.create(ClientDetailApi::class.java)
    override suspend fun getClientDetail(clientId: Int): Flow<Result<Pair<Int,ClientDetail?>>> = flow {
        try {
            val response =
                api.getClientDetail("client/client-detail/${clientId}/")
            if (response.code() == 200) {
                emit(Result.success(Pair(200,response.body())))
            }
            else {
                emit(Result.success(Pair(response.code(),null)))
            }

        } catch (e: Exception) {

        }
    }
}