package com.mobile.liderstoreagent.ui.viewmodels.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.reportmodel.ClientReportData
import com.mobile.liderstoreagent.domain.usecases.ClientReportUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.ClientReportUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class ClientReportViewModel:ViewModel() {

    private val useCase: ClientReportUseCase = ClientReportUseCaseImpl()
    val errorClientReportLiveData : LiveData<String> = useCase.errorClientReportLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<Any>()
    val errorTimeOutLiveData: LiveData<Unit> = useCase.errorTimeOut

    fun sendClientReportPackage(reportData: ClientReportData) {
        if(isConnected()){
            progressLiveData.value = true
            val lvd = useCase.sendClientReport(reportData)
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }




}