package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet

interface GetExpensesUseCase {
    val errorTimeOutLiveData: LiveData<Unit>
    val errorExpensesLiveData: LiveData<Unit>
    val errorExpensesNotFoundLiveData: LiveData<String>
    fun getExpenses(): LiveData<AgentExpenseGet>
}