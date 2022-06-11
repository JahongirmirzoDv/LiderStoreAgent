package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpensePost
import kotlinx.coroutines.flow.Flow

interface AgentExpenseRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun addExpense(data: AgentExpensePost): Flow<Result<Any?>>
}

