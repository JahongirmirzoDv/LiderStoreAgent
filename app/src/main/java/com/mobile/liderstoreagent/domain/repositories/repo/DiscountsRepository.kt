package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import kotlinx.coroutines.flow.Flow

interface DiscountsRepository {
    suspend fun getDiscounts() : Flow<Result<List<Discounts>?>>
}