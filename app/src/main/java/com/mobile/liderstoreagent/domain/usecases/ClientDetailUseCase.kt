package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail

interface ClientDetailUseCase {
    val errorClientDetailNotFoundLiveData: LiveData<String>
    val errorClientDetailLiveData: LiveData<Unit>
    fun getClientDetail(clientId: Int): LiveData<ClientDetail>
}