package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.LoginApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl : LoginRepository {
    private val api = ApiClient.retrofit.create(LoginApiInterface::class.java)
    override val errorTimeOutData = MutableLiveData<Unit>()
    override suspend fun userLogin(loginData: LoginData): Flow<Result<Pair<Int,LoginResponse?>>> = flow {
        try {
            val response = api.userLogin("application/json",loginData)
            if(response.code() == 200) {
                emit(Result.success(Pair(200,response.body())))
                TokenSaver.setPassword(loginData.password)
                TokenSaver.setLogin(loginData.username)
                TokenSaver.token = response.body()!!.token!!
                TokenSaver.setAgentId(response.body()!!.user!!.id)
                TokenSaver.setFirstName(response.body()!!.user!!.first_name)
                TokenSaver.setLastName(response.body()!!.user!!.last_name)
            }
            else {
                emit(Result.success(Pair(response.code(),null)))
            }

        } catch (e : Exception) {
            errorTimeOutData.postValue(Unit)
        }
    }

}