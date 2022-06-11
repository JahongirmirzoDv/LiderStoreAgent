package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.AddClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketCode
import kotlinx.coroutines.flow.Flow

interface AddClientRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun addClient(data: AddClientData): Flow<Result<Pair<Int,MarketCode?>>>
}

