package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse

interface LoginUseCase {
    val errorTimeOutLiveData: LiveData<Unit>
    val errorLoginLiveData : LiveData<String>
    fun userLogin(loginData: LoginData) : LiveData<LoginResponse>
}