package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import com.mobile.liderstoreagent.domain.repositories.impl.ClientDetailRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.ClientDetailRepo
import com.mobile.liderstoreagent.domain.usecases.ClientDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class ClientDetailUseCaseImpl : ClientDetailUseCase {

    private val repository: ClientDetailRepo = ClientDetailRepoImpl()
    override val errorClientDetailLiveData = MutableLiveData<Unit>()
    override val errorClientDetailNotFoundLiveData = MutableLiveData<String>()
    override fun getClientDetail(clientId: Int): LiveData<ClientDetail> = liveData(Dispatchers.IO) {
        repository.getClientDetail(clientId).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { pair ->
                    if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                    if (pair.first == 404||pair.first == 400||pair.first == 500) errorClientDetailNotFoundLiveData.postValue("Сервер билан боғлиқ хатолик!")
                }
            } else {
                errorClientDetailLiveData.postValue(Unit)
            }
        }
    }
}
