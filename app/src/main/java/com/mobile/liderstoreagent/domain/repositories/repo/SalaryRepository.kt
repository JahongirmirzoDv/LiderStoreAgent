package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.salarymodel.SalaryData
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {
    suspend fun getSalary(): Flow<Result<Pair<Int, SalaryData?>>>
}