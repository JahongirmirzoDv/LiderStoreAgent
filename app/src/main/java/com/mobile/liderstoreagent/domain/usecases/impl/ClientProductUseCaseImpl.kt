package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import com.mobile.liderstoreagent.domain.repositories.impl.ClientProductsRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.ClientProductsRepository
import com.mobile.liderstoreagent.domain.usecases.ClientProductsUseCase
import kotlinx.coroutines.flow.collect

class ClientProductUseCaseImpl : ClientProductsUseCase {
    private val repository: ClientProductsRepository = ClientProductsRepositoryImpl()
    override val errorClientProductsLiveData = MutableLiveData<Unit>()

    override fun getClientProducts(clientId: String): LiveData<List<ClientProducts>> = liveData {
        repository.getClientProducts(clientId).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorClientProductsLiveData.postValue(Unit)
            }
        }
    }
}