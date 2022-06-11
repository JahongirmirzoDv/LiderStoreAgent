package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpensePost
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.AgentExpenseApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.domain.repositories.repo.AgentExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class AgentExpenseRepositoryImpl : AgentExpenseRepository {

    private val api = ApiClient.retrofit.create(AgentExpenseApi::class.java)
    override val errorTimeOutData = MutableLiveData<Unit>()
    override suspend fun addExpense(data: AgentExpensePost): Flow<Result<Any?>> = flow {

        try {

            val name = data.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val description = data.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = data.category.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.addExpense(
                "application/json",
                name,
                description,
                data.quantity,
                category,
                false,
                TokenSaver.getAgentId(),
                data.expense_sub_category
            )

            when (response.code()) {
                201 -> {
                    emit(Result.success(response.body()))
                }
            }

        } catch (e: Exception) {
            errorTimeOutData.postValue(Unit)
        }
    }

}