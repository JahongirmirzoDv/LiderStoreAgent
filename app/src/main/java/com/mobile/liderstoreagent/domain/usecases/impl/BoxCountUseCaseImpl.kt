package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.agentbox.BoxCount
import com.mobile.liderstoreagent.domain.repositories.impl.BoxCountRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.BoxCountRepository
import com.mobile.liderstoreagent.domain.usecases.BoxCountUseCase
import kotlinx.coroutines.flow.collect

class BoxCountUseCaseImpl : BoxCountUseCase {
    private val repository: BoxCountRepository = BoxCountRepositoryImpl()
    override val errorExpensesLiveData = MutableLiveData<Unit>()
    override val errorTimeOutLiveData: LiveData<Unit>
        get() = repository.errorTimeOutData

    override fun getCounts(): LiveData<BoxCount> = liveData {
        repository.getBoxCounts().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorExpensesLiveData.postValue(Unit)
            }
        }


    }
}