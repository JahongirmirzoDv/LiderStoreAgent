package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.agentbox.BoxCount
import com.mobile.liderstoreagent.data.models.expense.AgentExpensePost
import com.mobile.liderstoreagent.data.models.expense.ExpenseCategories
import com.mobile.liderstoreagent.data.models.salarymodel.SalaryData
import com.mobile.liderstoreagent.domain.usecases.AgentExpenseUseCase
import com.mobile.liderstoreagent.domain.usecases.BoxCountUseCase
import com.mobile.liderstoreagent.domain.usecases.GetExpenseCategoriesUseCase
import com.mobile.liderstoreagent.domain.usecases.SalaryUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.AgentExpenseUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.BoxCountUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.GetExpenseCategoriesUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.SalaryUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class SalaryViewModel : ViewModel() {


    private val expenseUseCase: AgentExpenseUseCase = AgentExpenseUseCaseImpl()
    val errorAddExpenseLiveData: LiveData<String> = expenseUseCase.errorAddExpenseLiveData
    val errorTimeOutLiveDataAddClient: LiveData<Unit> = expenseUseCase.errorTimeOutAddClientLiveData
    val progressLiveData = MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val connectionErrorExpenseLiveData = MutableLiveData<Unit>()


    private val useCase: SalaryUseCase = SalaryUseCaseImpl()
    val errorNotResponseLiveData: LiveData<String> = useCase.errorNotResponseLiveData
    val errorResponseLiveData = MediatorLiveData<String>()
    val progressSellLiveData = MutableLiveData<Boolean>()
    val successExpenseAddedLiveData = MediatorLiveData<Any>()
    val successLiveData = MediatorLiveData<SalaryData>()




    private val expenseCategoriesUseCase: GetExpenseCategoriesUseCase = GetExpenseCategoriesUseCaseImpl()
    val errorGetExpenseLiveData: LiveData<Unit> = expenseCategoriesUseCase.errorCategoriesLiveData
    //val errorTimeOutLiveDataAddClient: LiveData<Unit> = expenseUseCase.errorTimeOutAddClientLiveData
    val successCategoryExpenseLiveData = MediatorLiveData<ExpenseCategories>()



    private val useCase1: BoxCountUseCase = BoxCountUseCaseImpl()
    val successLiveData1 = MediatorLiveData<BoxCount>()

    fun getBoxCount() {
        if(isConnected()){
            val liveData = useCase1.getCounts()
            successLiveData1.addSource(liveData) {
                successLiveData1.value = it
                successLiveData1.removeSource(liveData)
            }
        }
    }


    fun getExpenseCategories() {
        if(isConnected()){
            val liveData = expenseCategoriesUseCase.getExpenseCategories()
            successCategoryExpenseLiveData.addSource(liveData) {
                successCategoryExpenseLiveData.value = it
                successCategoryExpenseLiveData.removeSource(liveData)
            }
        }
    }



    init {
        val f = useCase.errorResponseLiveData
        errorResponseLiveData.addSource(f) {
            progressSellLiveData.value = false
            errorResponseLiveData.value = it
        }

        getSalary()
    }



    fun addExpense(data:AgentExpensePost) {
        if (isConnected()) {
            progressLiveData.value = true
            val lvd = expenseUseCase.addExpense(data)
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successExpenseAddedLiveData.value = it
                successExpenseAddedLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorExpenseLiveData.value = Unit
        }

    }

    fun getSalary() {
        if (isConnected()) {
            progressSellLiveData.value = true
            val lvd = useCase.getSalary()
            successLiveData.addSource(lvd) {
                progressSellLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }

    }
}