package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.agentbox.BoxCount
import kotlinx.coroutines.flow.Flow

interface BoxCountRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun getBoxCounts(): Flow<Result<BoxCount?>>
}

