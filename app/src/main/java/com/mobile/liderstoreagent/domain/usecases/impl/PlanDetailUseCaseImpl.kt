package com.mobile.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail
import com.mobile.liderstoreagent.domain.repositories.repo.PlanDetailRepository
import com.mobile.liderstoreagent.domain.repositories.impl.PlanDetailRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.PlanDetailUseCase
import kotlinx.coroutines.flow.collect


class PlanDetailUseCaseImpl : PlanDetailUseCase {
    override val errorEmptyResponseLiveData = MutableLiveData<String>()

    private val repository: PlanDetailRepository = PlanDetailRepositoryImpl()
    override val errorPlanDetailLiveData = MutableLiveData<Unit>()
    override fun getPlansDetail(planId:String): LiveData<List<PlanDetail>> = liveData {
        repository.getPlansDetail(planId).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { pair ->
                    if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                    if (pair.first == 404) errorEmptyResponseLiveData.postValue("Ma'lumot mavjud emas!")

                }
            } else {
                errorPlanDetailLiveData.postValue(Unit)
            }
        }
    }

}

