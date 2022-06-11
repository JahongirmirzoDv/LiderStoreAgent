package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.returnedmodel.ReturnedData
import kotlinx.coroutines.flow.Flow

interface ReturnedProductRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun returnedProduct(returnedProduct:ReturnedData): Flow<Result<Pair<Int, Any?>>>
}
