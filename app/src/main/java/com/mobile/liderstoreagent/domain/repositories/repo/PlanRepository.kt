package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.planmodel.PlanData
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    suspend fun getPlans(): Flow<Result<List<PlanData>?>>
}