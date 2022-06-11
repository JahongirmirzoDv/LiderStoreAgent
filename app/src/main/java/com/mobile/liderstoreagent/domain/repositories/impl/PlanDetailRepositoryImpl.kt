package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.PlanDetailApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.PlanDetailRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlanDetailRepositoryImpl : PlanDetailRepository {
    private val api = ApiClient.retrofit.create(PlanDetailApiInterface::class.java)

    override suspend fun getPlansDetail(planId: String):
            Flow<Result<Pair<Int, List<PlanDetail>?>>> = flow {
        try {
            val response =
                api.getPlanDetails("plan/plan-item-list/$planId/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(Pair(200,response.body())))
            }
            else if(response.code() == 404||response.code() == 500){
                emit(Result.success(Pair(404,null)))
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }
}


