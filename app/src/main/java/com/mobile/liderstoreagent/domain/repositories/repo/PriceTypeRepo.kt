package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType
import kotlinx.coroutines.flow.Flow

interface PriceTypeRepo {
    suspend fun getPriceType(): Flow<Result<PriceType?>>
}
