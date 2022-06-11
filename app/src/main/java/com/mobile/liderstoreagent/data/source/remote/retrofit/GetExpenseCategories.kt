package com.mobile.liderstoreagent.data.source.remote.retrofit

import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories
import retrofit2.Response
import retrofit2.http.GET

interface GetExpenseCategories {

    @GET("expense_discount/expense-category/")
    suspend fun getExpenseCategories(
    ): Response<ExpenseCategories>
}