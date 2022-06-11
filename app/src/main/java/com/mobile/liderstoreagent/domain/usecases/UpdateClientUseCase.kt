package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.UpdateClientData

interface UpdateClientUseCase {
    val errorUpdateClientLiveData: LiveData<String>
    var locationSt : String?
    val locationLiveData : LiveData<String?>
    fun updateClient(addClientData: UpdateClientData): LiveData<Any>
}