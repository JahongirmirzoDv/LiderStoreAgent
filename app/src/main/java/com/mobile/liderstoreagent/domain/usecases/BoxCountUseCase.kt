package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.agentbox.BoxCount

interface BoxCountUseCase {
    val errorTimeOutLiveData: LiveData<Unit>
    val errorExpensesLiveData: LiveData<Unit>
    fun getCounts(): LiveData<BoxCount>
}