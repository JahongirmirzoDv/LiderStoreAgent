package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.mobile.liderstoreagent.domain.usecases.SoldHistoryUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.SoldHistoryUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected


class SoldHistoryViewModel :ViewModel(){

    private val useCase: SoldHistoryUseCase = SoldHistoryUseCaseImpl()
    val errorHistoryLiveData : LiveData<Unit> = useCase.errorHistoryLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<List<SoldProductHistory>>()


    init {
        getHistory()
    }


    fun getHistory() {
        if(isConnected()){
            progressLiveData.value = true
            val liveData = useCase.soldHistory()
            successLiveData.addSource(liveData) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }


    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch(){
        closeLiveData.value = Unit
    }
}