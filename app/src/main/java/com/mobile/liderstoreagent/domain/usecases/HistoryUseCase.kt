package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.ToAgentSellOrder

interface HistoryUseCase {
    val errorHistoryLiveData: LiveData<Unit>
    fun setProduct(Id: String,data:ToAgentSellOrder): LiveData<ToAgentSellOrder>
    fun setProduct1(Id: String,data:ToAgentSellOrder): LiveData<ToAgentSellOrder>

}