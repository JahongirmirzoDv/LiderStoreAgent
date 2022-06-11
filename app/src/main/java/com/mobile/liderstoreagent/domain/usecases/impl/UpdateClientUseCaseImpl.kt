package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.UpdateClientData
import com.mobile.liderstoreagent.domain.repositories.UpdateClientRepository
import com.mobile.liderstoreagent.domain.repositories.UpdateClientRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.UpdateClientUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class UpdateClientUseCaseImpl private constructor() : UpdateClientUseCase {

    private val repository: UpdateClientRepository = UpdateClientRepositoryImpl()
    override val errorUpdateClientLiveData = MutableLiveData<String>()
    override val locationLiveData = MutableLiveData<String?>()

    override var locationSt: String?
        get() = ""
        set(value) {
            locationLiveData.value = value
        }

    companion object {
        private var useCaseImpl: UpdateClientUseCaseImpl? = null
        fun getUseCase(): UpdateClientUseCaseImpl {
            if (useCaseImpl == null)
                useCaseImpl = UpdateClientUseCaseImpl()
            return useCaseImpl!!
        }
    }

    override fun updateClient(addClientData: UpdateClientData): LiveData<Any> =
        liveData(Dispatchers.IO) {
            repository.updateClient(addClientData).collect {
                if (it.isSuccess) emit(it.getOrNull()!!)
                else errorUpdateClientLiveData.postValue("Error")
            }
        }

}