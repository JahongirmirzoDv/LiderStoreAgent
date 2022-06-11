package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.returnedmodel.ReturnedData
import com.mobile.liderstoreagent.domain.repositories.impl.ReturnedProductRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.ReturnedProductRepository
import com.mobile.liderstoreagent.domain.usecases.ReturnedProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class ReturnedProductUseCaseImpl : ReturnedProductUseCase {

    private val repository: ReturnedProductRepository = ReturnedProductRepoImpl()
    override val errorReturnedLiveData = MutableLiveData<Unit>()
    override val errorTimeOutLiveData: LiveData<Unit>
        get() = repository.errorTimeOutData
    override val errorResponseLiveData = MutableLiveData<String>()

    override fun returnedProduct(returnedData: ReturnedData): LiveData<Any> =
        liveData(Dispatchers.IO) {
            repository.returnedProduct(returnedData).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 404) errorResponseLiveData.postValue("Ушбу махсулотни қайтаришда хатолик юз берди!")
                    }
                } else {
                    errorReturnedLiveData.postValue(Unit)
                }
            }
        }
}
