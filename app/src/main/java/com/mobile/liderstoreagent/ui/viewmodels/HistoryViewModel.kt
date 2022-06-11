package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.domain.usecases.HistoryUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.HistoryUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class HistoryViewModel :ViewModel() {

    private val historyUseCase: HistoryUseCase = HistoryUseCaseImpl()
    val errorHistoryLiveData: LiveData<Unit> = historyUseCase.errorHistoryLiveData
    val progressHistoryLiveData = MutableLiveData<Boolean>()
    val connectionErrorHistoryLiveData = MutableLiveData<Unit>()
    val successHistoryLiveData = MediatorLiveData<ToAgentSellOrder>()

    fun getProduct(id:String, data:ToAgentSellOrder){
        if (isConnected()) {
            progressHistoryLiveData.value = true
            val liveData = historyUseCase.setProduct(id,data)
            successHistoryLiveData.addSource(liveData){
                progressHistoryLiveData.value = false
                successHistoryLiveData.value = it
                successHistoryLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorHistoryLiveData.value = Unit
        }
    }

    fun getProduct1(id:String, data:ToAgentSellOrder){
        if (isConnected()) {
            progressHistoryLiveData.value = true
            val liveData = historyUseCase.setProduct(id,data)
            successHistoryLiveData.addSource(liveData){
                progressHistoryLiveData.value = false
                successHistoryLiveData.value = it
                successHistoryLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorHistoryLiveData.value = Unit
        }
    }

}