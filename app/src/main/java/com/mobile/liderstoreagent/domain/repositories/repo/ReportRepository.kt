package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.reportmodel.ReportData
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun reportSend(data:ReportData): Flow<Result<Any?>>
}