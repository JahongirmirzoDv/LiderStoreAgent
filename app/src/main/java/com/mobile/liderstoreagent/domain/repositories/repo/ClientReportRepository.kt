package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.reportmodel.ClientReportData
import kotlinx.coroutines.flow.Flow

interface ClientReportRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun clientReportSend(data: ClientReportData): Flow<Result<Any?>>
}