package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories
import kotlinx.coroutines.flow.Flow

interface GetExpenseCategoriesRepo {
    suspend fun getExpenseCategories(): Flow<Result<ExpenseCategories?>>
}