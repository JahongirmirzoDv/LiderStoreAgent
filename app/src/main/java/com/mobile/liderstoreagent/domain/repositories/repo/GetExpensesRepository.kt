package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet
import kotlinx.coroutines.flow.Flow

interface GetExpensesRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun getExpenses(): Flow<Result<Pair<Int,AgentExpenseGet?>>>
}

