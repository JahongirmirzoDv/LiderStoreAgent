package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData

interface MarketSellUseCase {
    val errorResponseLiveData : LiveData<String>
    val errorOwnProductResponseLiveData : LiveData<String>
    val errorSellLiveData: LiveData<Unit>
    val errorOwnSellLiveData: LiveData<Unit>
    fun marketSell(list:List<MarketSellData>): LiveData<Any>
    fun marketOwnSell(list:List<SellToClientOwnProduct>): LiveData<Any>
}