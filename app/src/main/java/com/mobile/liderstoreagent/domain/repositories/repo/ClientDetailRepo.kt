package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import kotlinx.coroutines.flow.Flow

interface ClientDetailRepo {
    suspend fun getClientDetail(clientId: Int):Flow<Result<Pair<Int,ClientDetail?>>>
}