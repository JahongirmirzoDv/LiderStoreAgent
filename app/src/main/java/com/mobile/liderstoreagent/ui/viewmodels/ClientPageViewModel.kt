package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.domain.usecases.ClientsUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.ClientsUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class ClientPageViewModel:ViewModel() {

    private val useCase: ClientsUseCase = ClientsUseCaseImpl()
    val errorCategoriesLiveData : LiveData<Unit> = useCase.errorClientsLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val errorServerLiveData : LiveData<String> = useCase.errorClientsTextLiveData
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<List<ClientsData>>()

    init {
       getClients("")
    }

    fun getClients(filter:String) {
        if(isConnected()){
            progressLiveData.value = true
            val liveData = useCase.getClients(filter)
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