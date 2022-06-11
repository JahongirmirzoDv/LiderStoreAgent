package com.mobile.liderstoreagent.domain.usecases.localusecase

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory

interface ReportsHistoryUseCase {
    val errorLiveData : LiveData<String>
    val errorNotFoundLiveData : LiveData<String>
    fun getReports(clientId:String): LiveData<List<ReportHistory>>
}