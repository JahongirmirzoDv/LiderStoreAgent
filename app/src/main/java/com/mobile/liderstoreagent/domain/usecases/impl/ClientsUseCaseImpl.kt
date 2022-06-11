package com.mobile.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import com.mobile.liderstoreagent.domain.repositories.repo.ClientsRepository
import com.mobile.liderstoreagent.domain.repositories.impl.ClientsRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.ClientsUseCase
import kotlinx.coroutines.flow.collect

class ClientsUseCaseImpl : ClientsUseCase {
    private val repository: ClientsRepository = ClientsRepositoryImpl()
    override val errorClientsLiveData = MutableLiveData<Unit>()
    override val errorClientsTextLiveData = MutableLiveData<String>()
    override fun getClients(filter: String): LiveData<List<ClientsData>> = liveData {
        repository.getClients(filter).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { pair ->
                    if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                    if (pair.first == 500) errorClientsTextLiveData.postValue("Сервер билан боглиқ хатолик !")
                }
            } else {
                errorClientsLiveData.postValue(Unit)
            }
        }


    }
}