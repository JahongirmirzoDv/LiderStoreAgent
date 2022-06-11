package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail
import kotlinx.coroutines.flow.Flow

interface PlanDetailRepository {
    suspend fun getPlansDetail(planId:String): Flow<Result<Pair<Int, List<PlanDetail>?>>>
}