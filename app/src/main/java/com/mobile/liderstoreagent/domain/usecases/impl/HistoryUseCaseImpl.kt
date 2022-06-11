package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.ToAgentSellOrder
import com.mobile.liderstoreagent.domain.repositories.impl.HistoryRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.HistoryRepository
import com.mobile.liderstoreagent.domain.usecases.HistoryUseCase
import kotlinx.coroutines.flow.collect

class HistoryUseCaseImpl : HistoryUseCase {

    private val repository: HistoryRepository = HistoryRepositoryImpl()

    override val errorHistoryLiveData = MutableLiveData<Unit>()

    override fun setProduct(iD: String, data: ToAgentSellOrder): LiveData<ToAgentSellOrder> =
        liveData {
            repository.setProducts(iD, data).collect {
                if (it.isSuccess) {
                    emit(it.getOrNull()!!)
                } else {
                    errorHistoryLiveData.postValue(Unit)
                }
            }
        }

    override fun setProduct1(iD: String, data: ToAgentSellOrder): LiveData<ToAgentSellOrder> =
        liveData {
            repository.setProducts(iD, data).collect {
                if (it.isSuccess) {
                    emit(it.getOrNull()!!)
                } else {
                    errorHistoryLiveData.postValue(Unit)
                }
            }
        }
}