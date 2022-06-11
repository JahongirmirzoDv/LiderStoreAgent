package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.planmodel.PlanData

interface PlanUseCase {
    val errorPlanLiveData : LiveData<Unit>
    fun getPlans() : LiveData<List<PlanData>>
}