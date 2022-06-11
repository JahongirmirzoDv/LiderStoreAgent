package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail
import com.mobile.liderstoreagent.domain.repositories.impl.ProductDetailRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.ProductDetailRepository
import com.mobile.liderstoreagent.domain.usecases.ProductDetailUseCase
import kotlinx.coroutines.flow.collect

class ProductDetailUseCaseImpl : ProductDetailUseCase {
    private val repository: ProductDetailRepository = ProductDetailRepositoryImpl()
    override val errorProductLiveData = MutableLiveData<Unit>()
    override fun getProduct(productId: String): LiveData<ProductDetail> = liveData {
        repository.productFullData(productId).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorProductLiveData.postValue(Unit)
            }
        }
    }
}