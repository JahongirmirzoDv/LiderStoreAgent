package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun setProducts(id:String, data:ToAgentSellOrder) : Flow<Result<ToAgentSellOrder?>>

    suspend fun setProducts1(id:String, data:ToAgentSellOrder) : Flow<Result<ToAgentSellOrder?>>

}