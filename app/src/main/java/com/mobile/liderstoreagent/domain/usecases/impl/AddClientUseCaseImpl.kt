package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.AddClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketCode
import com.mobile.liderstoreagent.domain.repositories.impl.AddClientRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.AddClientRepository
import com.mobile.liderstoreagent.domain.usecases.AddClientUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class AddClientUseCaseImpl private constructor(): AddClientUseCase {

    private val repository: AddClientRepository = AddClientRepositoryImpl()
    override val errorAddClientLiveData = MutableLiveData<String>()
    override val errorAddClientServerLiveData = MutableLiveData<String>()
    override val locationLiveData= MutableLiveData<String?>()
    override val errorTimeOutAddClientLiveData: LiveData<Unit>
        get() = repository.errorTimeOutData



    override var locationSt: String?
        get() = ""
        set(value) {
            locationLiveData.value = value
        }

    companion object {
        private var useCaseImpl : AddClientUseCaseImpl?= null
        fun getUseCase() : AddClientUseCaseImpl {
            if (useCaseImpl == null)
                useCaseImpl = AddClientUseCaseImpl()
            return useCaseImpl!!
        }
    }

    override fun addClient(addClientData: AddClientData): LiveData<MarketCode> =
            liveData(Dispatchers.IO) {
                repository.addClient(addClientData).collect {
                    if (it.isSuccess) {
                        it.getOrNull()?.let { pair ->
                            if (pair.first == 201) pair.second?.let { it1 -> emit(it1) }
                            if (pair.first == 404||pair.first == 400||pair.first == 500)
                                errorAddClientServerLiveData.postValue("Килент  маълумотларида ёки серверда хатолик бор!")
                        }
                    }
                    else errorAddClientLiveData.postValue("Error")

}
            }
}