package com.mobile.liderstoreagent.ui.viewmodels.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.reportmodel.ReportData
import com.mobile.liderstoreagent.domain.usecases.ReportUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.ReportUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class ReportViewModel:ViewModel() {

    private val useCase: ReportUseCase = ReportUseCaseImpl()
    val errorReportLiveData : LiveData<String> = useCase.errorReportLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<Any>()



    fun sendPackage(reportData: ReportData) {
        if(isConnected()){
            progressLiveData.value = true
            val lvd = useCase.sendReport(reportData)
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