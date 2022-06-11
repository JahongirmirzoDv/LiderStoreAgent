package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.AddClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketCode

interface AddClientUseCase {
    val errorAddClientLiveData: LiveData<String>
    val errorTimeOutAddClientLiveData: LiveData<Unit>
    var locationSt : String?
    val locationLiveData : LiveData<String?>

    val errorAddClientServerLiveData: LiveData<String>
    fun addClient(addClientData: AddClientData): LiveData<MarketCode>
}

//60.25561;42.1511556