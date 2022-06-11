package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.CategoryApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.CategoryRepository
import com.mobile.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl : CategoryRepository {

    private val api = ApiClient.retrofit.create(CategoryApiInterface::class.java)
    override suspend fun getCategories(): Flow<Result<List<CategoryData>?>>
    = flow {
        try {
            val response = api.getCategories()
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }

        } catch (e: Exception) {
         //   emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xato!")
        }
    }
}