package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductData
import com.mobile.liderstoreagent.data.models.sellmodel.SellProductResponse
import com.mobile.liderstoreagent.domain.repositories.repo.SellProductRepository
import com.mobile.liderstoreagent.domain.repositories.impl.SellProductRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.SellProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class SellProductUseCaseImpl : SellProductUseCase {
    private val repository: SellProductRepository = SellProductRepositoryImpl()
    override val errorNotResponseLiveData = MutableLiveData<String>()
    override val errorResponseLiveData= MutableLiveData<String>()

    override fun sellProduct(sellProductData: SellProductData): LiveData<SellProductResponse> =
        liveData(Dispatchers.IO) {
            repository.sellProduct(sellProductData).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 201) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 400) errorResponseLiveData.postValue("Omborda maxsulot yetarli emas!")
                    }
                } else {
                    errorNotResponseLiveData.postValue("Error")
                }
            }
        }

}