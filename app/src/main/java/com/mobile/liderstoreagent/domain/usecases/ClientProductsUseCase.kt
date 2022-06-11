package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts

interface ClientProductsUseCase {
    val errorClientProductsLiveData: LiveData<Unit>
    fun getClientProducts(clientId: String): LiveData<List<ClientProducts>>
}