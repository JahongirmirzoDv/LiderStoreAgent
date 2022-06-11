package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.planmodel.PlanData
import com.mobile.liderstoreagent.domain.repositories.repo.PlanRepository
import com.mobile.liderstoreagent.domain.repositories.impl.PlanRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.PlanUseCase
import kotlinx.coroutines.flow.collect

class PlanUseCaseImpl : PlanUseCase {
    private val repository: PlanRepository = PlanRepositoryImpl()
    override val errorPlanLiveData = MutableLiveData<Unit>()
    override fun getPlans(): LiveData<List<PlanData>> = liveData {
        repository.getPlans().collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorPlanLiveData.postValue(Unit)
            }
        }
    }

}

