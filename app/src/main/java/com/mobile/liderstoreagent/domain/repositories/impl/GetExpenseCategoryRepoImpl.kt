package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.GetExpenseCategories
import com.mobile.liderstoreagent.domain.repositories.repo.GetExpenseCategoriesRepo
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenseCategoryRepoImpl : GetExpenseCategoriesRepo {

    private val api = ApiClient.retrofit.create(GetExpenseCategories::class.java)
    override suspend fun getExpenseCategories(): Flow<Result<ExpenseCategories?>> = flow {
        try {
            val response = api.getExpenseCategories()
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xato!")
        }
    }
}