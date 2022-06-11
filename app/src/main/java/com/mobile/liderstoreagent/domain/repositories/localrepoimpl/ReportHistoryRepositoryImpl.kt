package com.mobile.liderstoreagent.domain.repositories.localrepoimpl

import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ReportHistoryApi
import com.mobile.liderstoreagent.domain.repositories.localrepo.ReportHistoryRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReportHistoryRepositoryImpl : ReportHistoryRepository {

    private val api = ApiClient.retrofit.create(ReportHistoryApi::class.java)

    override suspend fun getReports(clientId: String): Flow<Result<Pair<Int,List<ReportHistory>?>>> = flow {
        try {
            val response = api.getHistory("warehouse/agent-report-list/", clientId)
            if (response.code() == 200) {
                emit(Result.success(Pair(200,response.body())))
                log(response.body().toString(), "QQQ")
            }
            else{
                emit(Result.success(Pair(response.code(),null)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e")
        }
    }


}