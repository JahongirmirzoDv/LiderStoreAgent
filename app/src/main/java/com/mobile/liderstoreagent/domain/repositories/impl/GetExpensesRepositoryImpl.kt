package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.GetAgentExpenseList
import com.mobile.liderstoreagent.domain.repositories.repo.GetExpensesRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpensesRepositoryImpl : GetExpensesRepository {
    override val errorTimeOutData = MutableLiveData<Unit>()
    private val api = ApiClient.retrofit.create(GetAgentExpenseList::class.java)
    override suspend fun getExpenses(): Flow<Result<Pair<Int,AgentExpenseGet?>>> = flow {


        try {
            val response =
                api.getAgentExpenseList("expense_discount/expense-staff-list/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(Pair(200,response.body())))
            } else{
                emit(Result.success(Pair(response.code(),null)))
            }

        } catch (e: Exception) {
            log("$e","NAMUNA")
            errorTimeOutData.postValue(Unit)
        }
    }

}