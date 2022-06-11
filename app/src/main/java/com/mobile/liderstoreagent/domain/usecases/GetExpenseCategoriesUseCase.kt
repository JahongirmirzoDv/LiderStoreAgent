package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories

interface GetExpenseCategoriesUseCase {
    val errorCategoriesLiveData: LiveData<Unit>
    fun getExpenseCategories(): LiveData<ExpenseCategories>
}