package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import kotlinx.coroutines.flow.Flow

interface WarehouseRepository {
    suspend fun getWarehouses(): Flow<Result<WarehouseData?>>
    suspend fun getOwnCategories(): Flow<Result<AgentData?>>
}