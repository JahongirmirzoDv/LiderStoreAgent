package com.mobile.liderstoreagent.domain.repositories.localrepo

import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import kotlinx.coroutines.flow.Flow

interface ReportHistoryRepository {
    suspend fun getReports(clientId:String): Flow<Result<Pair<Int,List<ReportHistory>?>>>
}