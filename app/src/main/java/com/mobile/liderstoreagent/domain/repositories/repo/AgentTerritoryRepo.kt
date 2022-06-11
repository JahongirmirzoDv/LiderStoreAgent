package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.clientmodel.Territory
import kotlinx.coroutines.flow.Flow

interface AgentTerritoryRepo {
    suspend fun getTerritories(): Flow<Result<List<Territory>?>>
}