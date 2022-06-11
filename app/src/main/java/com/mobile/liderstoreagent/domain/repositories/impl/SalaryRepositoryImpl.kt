package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.salarymodel.SalaryData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.SalaryApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.SalaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SalaryRepositoryImpl : SalaryRepository {
    private val api = ApiClient.retrofit.create(SalaryApiInterface::class.java)
    override suspend fun getSalary():
            Flow<Result<Pair<Int, SalaryData?>>> = flow {
        try {
            val response = api.getSalary("salary/agents-income/${TokenSaver.getAgentId()}/")
            val code = response.code()
            if (code == 200) {
                emit(Result.success(Pair(200, response.body())))
            } else if (code == 500 || code == 400 || code == 404) {
                emit(Result.success(Pair(500, null)))
            }
        } catch (e: Exception) {
        }
    }
}