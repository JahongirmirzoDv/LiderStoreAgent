package com.mobile.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts
import com.mobile.liderstoreagent.domain.repositories.repo.DiscountsRepository
import com.mobile.liderstoreagent.domain.repositories.impl.DiscountsRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.DiscountsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class DiscountUseCaseImpl : DiscountsUseCase {

    private val repository: DiscountsRepository = DiscountsRepositoryImpl()
    override val errorDiscountsLiveData = MutableLiveData<Unit>()

    override fun getDiscounts(): LiveData<List<Discounts>> = liveData(Dispatchers.IO) {
        repository.getDiscounts().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorDiscountsLiveData.postValue(Unit)
            }
        }
    }
}
