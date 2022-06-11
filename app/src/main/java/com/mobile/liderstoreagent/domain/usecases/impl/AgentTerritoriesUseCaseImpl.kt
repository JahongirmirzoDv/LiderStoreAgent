package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.Territory
import com.mobile.liderstoreagent.domain.repositories.impl.AgentTerritoryRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.AgentTerritoryRepo
import com.mobile.liderstoreagent.domain.usecases.AgentTerritoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class AgentTerritoriesUseCaseImpl : AgentTerritoriesUseCase {

    private val repository: AgentTerritoryRepo = AgentTerritoryRepoImpl()
    override val errorTerritoryLiveData = MutableLiveData<Unit>()

    override fun getTerritories(): LiveData<List<Territory>> = liveData(Dispatchers.IO) {
        repository.getTerritories().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorTerritoryLiveData.postValue(Unit)
            }
        }
    }
}
