package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpensePost

interface AgentExpenseUseCase {
    val errorAddExpenseLiveData: LiveData<String>
    val errorTimeOutAddClientLiveData: LiveData<Unit>
    fun addExpense(data: AgentExpensePost): LiveData<Any>
}
