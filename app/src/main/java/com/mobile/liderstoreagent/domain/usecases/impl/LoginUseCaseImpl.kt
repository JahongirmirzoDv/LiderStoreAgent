package com.mobile.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginData
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse
import com.mobile.liderstoreagent.domain.repositories.repo.LoginRepository
import com.mobile.liderstoreagent.domain.repositories.impl.LoginRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect


class LoginUseCaseImpl : LoginUseCase {
    private val repository: LoginRepository = LoginRepositoryImpl()
    override val errorLoginLiveData = MutableLiveData<String>()
    override val errorTimeOutLiveData: LiveData<Unit>
        get() = repository.errorTimeOutData
    override fun userLogin(loginData: LoginData): LiveData<LoginResponse> =
        liveData(Dispatchers.IO) {
            repository.userLogin(loginData).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 404||pair.first == 400) errorLoginLiveData.postValue("Логин ёки парол хато!!")
                    }
                }
                else errorLoginLiveData.postValue("Error")
            }
        }

}