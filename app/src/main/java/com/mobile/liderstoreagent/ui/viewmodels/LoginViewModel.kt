package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.loginmodel.LoginData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse
import com.mobile.liderstoreagent.domain.usecases.LoginUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.LoginUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class LoginViewModel : ViewModel() {

    private val useCase: LoginUseCase = LoginUseCaseImpl()
    val errorLoginLiveData : LiveData<String> = useCase.errorLoginLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<LoginResponse>()
    val errorTimeOutLive: LiveData<Unit> = useCase.errorTimeOutLiveData

    fun login(userName : String, password:String) {
        if(isConnected()){
            progressLiveData.value = true
            val lvd = useCase.userLogin(LoginData(userName,password))
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }
    }
}