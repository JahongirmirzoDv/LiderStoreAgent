package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.salarymodel.SalaryData

interface SalaryUseCase {
    val errorNotResponseLiveData : LiveData<String>
    val errorResponseLiveData : LiveData<String>
    fun getSalary() : LiveData<SalaryData>

}