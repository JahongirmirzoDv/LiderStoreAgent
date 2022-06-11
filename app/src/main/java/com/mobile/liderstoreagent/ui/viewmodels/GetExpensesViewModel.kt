package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.expense.AgentExpenseGet
import com.mobile.liderstoreagent.domain.usecases.GetExpensesUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.GetExpensesUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class GetExpensesViewModel :ViewModel(){

    private val useCase: GetExpensesUseCase = GetExpensesUseCaseImpl()
    val errorExpensesLiveData: LiveData<Unit> = useCase.errorExpensesLiveData
    val errorTimeOutLiveData: LiveData<Unit> = useCase.errorTimeOutLiveData
    val errorExpensesNotFoundLiveData: LiveData<String> = useCase.errorExpensesNotFoundLiveData
    val progressLiveData = MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<AgentExpenseGet>()


    init {
        getExpenses()
    }

    fun getExpenses() {
        if(isConnected()){
            progressLiveData.value = true
            val liveData = useCase.getExpenses()
            successLiveData.addSource(liveData) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }


    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch(){
        closeLiveData.value = Unit
    }



}