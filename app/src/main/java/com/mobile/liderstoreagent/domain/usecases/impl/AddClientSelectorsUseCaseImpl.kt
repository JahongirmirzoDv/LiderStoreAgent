package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import com.mobile.liderstoreagent.domain.repositories.impl.AddClientSelectorsRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.AddClientSelectorsRepository
import com.mobile.liderstoreagent.domain.usecases.localusecaseimpl.AddClientSelectorsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class AddClientSelectorsUseCaseImpl : AddClientSelectorsUseCase {

    private val repository: AddClientSelectorsRepository = AddClientSelectorsRepoImpl()
    override val errorSelectorsLiveData = MutableLiveData<Unit>()

    override val errorTimeOutSelect: LiveData<Unit>
        get() = repository.errorTimeOutData

    override fun getSelectors(): LiveData<AddClientSelectors> = liveData(Dispatchers.IO) {

        repository.getAddClientSelectors().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorSelectorsLiveData.postValue(Unit)
            }
        }
    }
}
