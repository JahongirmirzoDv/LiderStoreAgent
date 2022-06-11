package com.mobile.liderstoreagent.ui.viewmodels.reporthistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import com.mobile.liderstoreagent.domain.usecases.localusecase.ReportsHistoryUseCase
import com.mobile.liderstoreagent.domain.usecases.localusecaseimpl.ReportsHistoryUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class ReportHistoryViewModel : ViewModel() {
    private val useCase: ReportsHistoryUseCase = ReportsHistoryUseCaseImpl()
    val errorLiveData: LiveData<String> = useCase.errorLiveData
    val errorNotFoundLiveData: LiveData<String> = useCase.errorNotFoundLiveData
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val progressLiveData = MutableLiveData<Boolean>()
    val successLiveDataGet = MediatorLiveData<List<ReportHistory>>()

    init {
        getReports("")
    }
    fun getReports(clientId:String) {
        if (isConnected()) {
            progressLiveData.value = true
            val lvd = useCase.getReports(clientId)
            successLiveDataGet.addSource(lvd) {
                progressLiveData.value = false
                successLiveDataGet.value = it
                successLiveDataGet.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }
    }

    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch(){
        closeLiveData.value = Unit
    }


}