package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.reportmodel.ClientReportData
import com.mobile.liderstoreagent.domain.repositories.impl.ClientReportRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.ClientReportRepository
import com.mobile.liderstoreagent.domain.usecases.ClientReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect


class ClientReportUseCaseImpl : ClientReportUseCase {

    private val repository: ClientReportRepository = ClientReportRepositoryImpl()
    override val errorClientReportLiveData = MutableLiveData<String>()
    override val errorTimeOut: LiveData<Unit>
        get() = repository.errorTimeOutData

    override fun sendClientReport(reportData: ClientReportData): LiveData<Any> =
        liveData(Dispatchers.IO) {
            repository.clientReportSend(reportData).collect {
                if (it.isSuccess) emit(it.getOrNull()!!)
                else errorClientReportLiveData.postValue("Error")
            }
        }


}