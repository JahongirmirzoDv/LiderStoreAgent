package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet
import com.mobile.liderstoreagent.domain.repositories.impl.GetExpensesRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.GetExpensesRepository
import com.mobile.liderstoreagent.domain.usecases.GetExpensesUseCase
import kotlinx.coroutines.flow.collect

class GetExpensesUseCaseImpl : GetExpensesUseCase {
    private val repository: GetExpensesRepository = GetExpensesRepositoryImpl()
    override val errorExpensesLiveData = MutableLiveData<Unit>()
    override val errorExpensesNotFoundLiveData = MutableLiveData<String>()
    override val errorTimeOutLiveData: LiveData<Unit>
        get() = repository.errorTimeOutData

    override fun getExpenses(): LiveData<AgentExpenseGet> = liveData {
        repository.getExpenses().collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { pair ->
                    if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                    if (pair.first == 404||pair.first == 400||pair.first == 500) errorExpensesNotFoundLiveData.postValue("Сервер билан боғлиқ хатолик!")
                }
            } else {
                errorExpensesLiveData.postValue(Unit)
            }
        }


    }
}