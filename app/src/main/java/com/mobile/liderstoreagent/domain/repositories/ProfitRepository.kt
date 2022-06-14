package com.mobile.liderstoreagent.domain.repositories

import com.mobile.liderstoreagent.data.models.Profit
import kotlinx.coroutines.flow.Flow

interface ProfitRepository {
    suspend fun getProfit(
        id: String,
        start_date: String,
        to_date: String,
    ) :Flow<Result<Profit?>>
}