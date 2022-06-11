package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories
import com.mobile.liderstoreagent.domain.repositories.impl.GetExpenseCategoryRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.GetExpenseCategoriesRepo
import com.mobile.liderstoreagent.domain.usecases.GetExpenseCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class GetExpenseCategoriesUseCaseImpl : GetExpenseCategoriesUseCase {

    private val repository: GetExpenseCategoriesRepo = GetExpenseCategoryRepoImpl()
    override val errorCategoriesLiveData = MutableLiveData<Unit>()

    override fun getExpenseCategories(): LiveData<ExpenseCategories> = liveData(Dispatchers.IO) {
        repository.getExpenseCategories().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorCategoriesLiveData.postValue(Unit)
            }
        }
    }
}
