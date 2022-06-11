package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import kotlinx.coroutines.flow.Flow

interface MarketSellRepository {
    suspend fun marketProducts(list: List<MarketSellData>): Flow<Result<Pair<Int, Any?>>>
    suspend fun marketOwnProducts(list: List<SellToClientOwnProduct>): Flow<Result<Pair<Int, Any?>>>
}
