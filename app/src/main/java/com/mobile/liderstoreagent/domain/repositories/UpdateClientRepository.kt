package com.mobile.liderstoreagent.domain.repositories

import com.mobile.liderstoreagent.data.models.clientmodel.UpdateClientData
import kotlinx.coroutines.flow.Flow

interface UpdateClientRepository {
    suspend fun updateClient(data: UpdateClientData): Flow<Result<Any?>>
}

