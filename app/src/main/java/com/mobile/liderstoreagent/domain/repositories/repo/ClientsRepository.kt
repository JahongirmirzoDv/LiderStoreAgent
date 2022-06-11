package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.clientmodel.ClientsData
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
    suspend fun getClients(filter:String): Flow<Result<Pair<Int,List<ClientsData>?>>>
}

