package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.Profit
import com.mobile.liderstoreagent.domain.repositories.ProfitRepository
import com.mobile.liderstoreagent.domain.repositories.impl.ProfitRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class ProfitUseCase {
    private val repository: ProfitRepository = ProfitRepositoryImpl()
    val errorSelectorsLiveData = MutableLiveData<Unit>()

    fun getSelectors(
        id: String,
        start_date: String,
        to_date: String,
    ): Flow<Profit> = flow {
        repository.getProfit(id, start_date, to_date).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorSelectorsLiveData.postValue(Unit)
            }
        }
    }
}