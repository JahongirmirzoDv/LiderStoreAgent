package com.mobile.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    val errorTimeOutData: LiveData<Unit>
    suspend fun userLogin(loginData: LoginData) : Flow<Result<Pair<Int,LoginResponse?>>>
}