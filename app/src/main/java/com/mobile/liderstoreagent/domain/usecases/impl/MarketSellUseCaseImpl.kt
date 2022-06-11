package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import com.mobile.liderstoreagent.domain.repositories.impl.MarketSellRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.MarketSellRepository
import com.mobile.liderstoreagent.domain.usecases.MarketSellUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class MarketSellUseCaseImpl : MarketSellUseCase {

    private val repository: MarketSellRepository = MarketSellRepoImpl()
    override val errorSellLiveData = MutableLiveData<Unit>()
    override val errorOwnSellLiveData = MutableLiveData<Unit>()
    override val errorResponseLiveData = MutableLiveData<String>()
    override val errorOwnProductResponseLiveData = MutableLiveData<String>()

    override fun marketSell(list: List<MarketSellData>): LiveData<Any> = liveData(Dispatchers.IO) {
        repository.marketProducts(list).collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { pair ->
                    if (pair.first == 201) pair.second?.let { it1 -> emit(it1) }
                    if (pair.first == 400) errorResponseLiveData.postValue("Omborda maxsulot yetarli emas!")
                }
            } else {
                errorSellLiveData.postValue(Unit)
            }
        }
    }

    override fun marketOwnSell(list: List<SellToClientOwnProduct>): LiveData<Any> =
        liveData(Dispatchers.IO) {
            repository.marketOwnProducts(list).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 201) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 400) errorOwnProductResponseLiveData.postValue("O`zida maxsulot yetarli emas!")
                    }
                } else {
                    errorOwnSellLiveData.postValue(Unit)
                }
            }
        }

}
