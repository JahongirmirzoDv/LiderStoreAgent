package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData

interface ClientsUseCase {
    val errorClientsTextLiveData : LiveData<String>
    val errorClientsLiveData : LiveData<Unit>
    fun getClients(filter:String) : LiveData<List<ClientsData>>
}