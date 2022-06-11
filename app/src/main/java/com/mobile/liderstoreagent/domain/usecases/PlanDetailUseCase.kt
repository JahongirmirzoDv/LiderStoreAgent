package com.mobile.liderstoreagent.domain.usecases


import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail

interface PlanDetailUseCase {
    val errorEmptyResponseLiveData : LiveData<String>
    val errorPlanDetailLiveData : LiveData<Unit>
    fun getPlansDetail(planId:String) : LiveData<List<PlanDetail>>
}