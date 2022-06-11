package com.mobile.liderstoreagent.domain.usecases.localusecaseimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.reportmodel.ReportHistory
import com.mobile.liderstoreagent.domain.repositories.localrepo.ReportHistoryRepository
import com.mobile.liderstoreagent.domain.repositories.localrepoimpl.ReportHistoryRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.localusecase.ReportsHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class ReportsHistoryUseCaseImpl : ReportsHistoryUseCase {
    override val errorLiveData = MutableLiveData<String>()
    override val errorNotFoundLiveData = MutableLiveData<String>()
    private val repository: ReportHistoryRepository = ReportHistoryRepositoryImpl()

    override fun getReports(clientId:String): LiveData<List<ReportHistory>> =
        liveData(Dispatchers.IO){
            repository.getReports(clientId).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 404||pair.first == 400) errorNotFoundLiveData.postValue("Топилмади!")
                    }
                }
                else errorLiveData.postValue("Xatolik!")
            }
        }

}