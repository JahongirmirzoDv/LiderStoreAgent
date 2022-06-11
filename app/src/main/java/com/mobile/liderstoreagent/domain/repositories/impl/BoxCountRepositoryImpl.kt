package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.data.models.agentbox.BoxCount
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.BoxCountApi
import com.mobile.liderstoreagent.domain.repositories.repo.BoxCountRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BoxCountRepositoryImpl : BoxCountRepository {
    override val errorTimeOutData = MutableLiveData<Unit>()
    private val api = ApiClient.retrofit.create(BoxCountApi::class.java)
    override suspend fun getBoxCounts(): Flow<Result<BoxCount?>> = flow {

        try {
            val response =
                api.getBoxCount("warehouse/agent-box-count/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }


        } catch (e: Exception) {
            log("$e", "NAMUNA")
            errorTimeOutData.postValue(Unit)
        }
    }

}