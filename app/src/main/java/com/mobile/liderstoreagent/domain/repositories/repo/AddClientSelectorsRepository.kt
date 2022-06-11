package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import kotlinx.coroutines.flow.Flow

interface AddClientSelectorsRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun getAddClientSelectors(): Flow<Result<AddClientSelectors?>>
}
