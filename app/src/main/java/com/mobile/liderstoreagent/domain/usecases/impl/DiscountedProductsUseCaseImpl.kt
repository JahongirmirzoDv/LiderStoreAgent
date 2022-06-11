package com.mobile.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.mobile.liderstoreagent.domain.repositories.repo.DiscountedProductsRepository
import com.mobile.liderstoreagent.domain.repositories.impl.DiscountedProductsRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.DiscountedProductsUseCase
import kotlinx.coroutines.flow.collect

class DiscountedProductsUseCaseImpl : DiscountedProductsUseCase {

    private val repository: DiscountedProductsRepository = DiscountedProductsRepositoryImpl()
    override val errorDiscountedProductsLiveData = MutableLiveData<Unit>()

    override fun getDiscountedProducts(discountId: String): LiveData<List<DiscountedProduct>> = liveData {
        repository.getDiscountedProducts(discountId).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorDiscountedProductsLiveData.postValue(Unit)
            }
        }
    }
}