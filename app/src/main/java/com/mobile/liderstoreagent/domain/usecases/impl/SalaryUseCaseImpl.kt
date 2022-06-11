package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.salarymodel.SalaryData
import com.mobile.liderstoreagent.domain.repositories.repo.SalaryRepository
import com.mobile.liderstoreagent.domain.repositories.impl.SalaryRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.SalaryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class SalaryUseCaseImpl : SalaryUseCase {
    private val repository: SalaryRepository = SalaryRepositoryImpl()
    override val errorNotResponseLiveData = MutableLiveData<String>()
    override val errorResponseLiveData = MutableLiveData<String>()

    override fun getSalary(): LiveData<SalaryData> =
        liveData(Dispatchers.IO) {
            repository.getSalary().collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 500) errorResponseLiveData.postValue("Oylik ma'lumotlar topilmadi!")
                    }
                } else {
                    errorNotResponseLiveData.postValue("Error")
                }
            }
        }

}