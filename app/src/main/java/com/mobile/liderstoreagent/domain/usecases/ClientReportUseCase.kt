package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.reportmodel.ClientReportData

interface ClientReportUseCase {
    val errorTimeOut: LiveData<Unit>
    val errorClientReportLiveData : LiveData<String>
    fun sendClientReport(reportData: ClientReportData) : LiveData<Any>
}