package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import com.mobile.liderstoreagent.domain.repositories.impl.ProductsRepositoryIml
import com.mobile.liderstoreagent.domain.repositories.repo.ProductsRepository
import com.mobile.liderstoreagent.domain.usecases.ProductsUseCase
import kotlinx.coroutines.flow.collect

class ProductsUseCaseImpl : ProductsUseCase {

    private val repository: ProductsRepository = ProductsRepositoryIml()
    override val errorProductsLiveData = MutableLiveData<Unit>()
    override val errorOwnProductsLiveData = MutableLiveData<Unit>()

    override fun getProducts(
        categoryId: String,
        clientId: String,
        code: String,
        name: String,
    ): LiveData<List<ProductData>> = liveData {

        repository.getProducts(categoryId, clientId, code, name).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorProductsLiveData.postValue(Unit)
            }
        }
    }

    override fun getOwnProducts(categoryId: String, clientId: String): LiveData<OwnProduct> =
        liveData {
            repository.getOwnProducts(categoryId, clientId).collect {
                if (it.isSuccess) {
                    emit(it.getOrNull()!!)
                } else {
                    errorOwnProductsLiveData.postValue(Unit)
                }
            }
        }
}