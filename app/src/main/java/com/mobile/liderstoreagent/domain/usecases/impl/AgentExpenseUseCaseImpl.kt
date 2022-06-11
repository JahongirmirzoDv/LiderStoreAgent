package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpensePost
import com.mobile.liderstoreagent.domain.repositories.impl.AgentExpenseRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.AgentExpenseRepository
import com.mobile.liderstoreagent.domain.usecases.AgentExpenseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class AgentExpenseUseCaseImpl : AgentExpenseUseCase {

    private val repository: AgentExpenseRepository = AgentExpenseRepositoryImpl()
    override val errorAddExpenseLiveData = MutableLiveData<String>()
    override val errorTimeOutAddClientLiveData: LiveData<Unit>
        get() = repository.errorTimeOutData

    override fun addExpense(data: AgentExpensePost): LiveData<Any> =
        liveData(Dispatchers.IO) {
            repository.addExpense(data).collect {
                if (it.isSuccess) emit(it.getOrNull()!!)
                else errorAddExpenseLiveData.postValue("Error")
            }
        }
}