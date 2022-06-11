package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.Territory

interface AgentTerritoriesUseCase {
    val errorTerritoryLiveData: LiveData<Unit>
    fun getTerritories(): LiveData<List<Territory>>
}