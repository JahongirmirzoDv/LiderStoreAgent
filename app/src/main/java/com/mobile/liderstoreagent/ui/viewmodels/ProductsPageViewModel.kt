package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.*
import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData
import com.mobile.liderstoreagent.data.models.productsmodel.ProductData
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import com.mobile.liderstoreagent.data.models.warehouse_product.OwnProduct
import com.mobile.liderstoreagent.domain.usecases.CategoriesUseCase
import com.mobile.liderstoreagent.domain.usecases.MarketSellUseCase
import com.mobile.liderstoreagent.domain.usecases.ProductsUseCase
import com.mobile.liderstoreagent.domain.usecases.WarehousesUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.CategoriesUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.MarketSellUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.ProductsUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.WarehousesUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class ProductsPageViewModel : ViewModel() {

    private val categoriesUseCase: CategoriesUseCase = CategoriesUseCaseImpl()
    val errorCategoriesLiveData: LiveData<Unit> = categoriesUseCase.errorCategoriesLiveData
    val progressCategoriesLiveData = MutableLiveData<Boolean>()
    val connectionErrorCategoriesLiveData = MutableLiveData<Unit>()
    val successCategoriesLiveData = MediatorLiveData<List<CategoryData>>()

    private val warehouseUseCase: WarehousesUseCase = WarehousesUseCaseImpl()
    val errorWarehouseLiveData: LiveData<Unit> = warehouseUseCase.errorWarehousesLiveData
    val progressWarehouseLiveData = MutableLiveData<Boolean>()
    val connectionErrorWarehouseLiveData = MutableLiveData<Unit>()
    val successWarehouseLiveData = MediatorLiveData<WarehouseData>()

    val errorOwnCategoriesLiveData: LiveData<Unit> = warehouseUseCase.errorOwnCategoriesLiveData
    val progressOwnCategoriesLiveData = MutableLiveData<Boolean>()
    val connectionErrorOwnCategoriesLiveData = MutableLiveData<Unit>()
    val successOwnCategoriesLiveData = MediatorLiveData<AgentData>()

    private val productsUseCase: ProductsUseCase = ProductsUseCaseImpl()
    val errorProductsLiveData: LiveData<Unit> = productsUseCase.errorProductsLiveData
    val progressProductsLiveData = MutableLiveData<Boolean>()
    val connectionErrorProductsLiveData = MutableLiveData<Unit>()
    val successProductsLiveData = MediatorLiveData<List<ProductData>>()

    val errorOwnProductsLiveData: LiveData<Unit> = productsUseCase.errorOwnProductsLiveData
    val progressOwnProductsLiveData = MutableLiveData<Boolean>()
    val connectionErrorOwnProductsLiveData = MutableLiveData<Unit>()
    val successOwnProductsLiveData = MediatorLiveData<OwnProduct>()

    private val marketSellUseCase: MarketSellUseCase = MarketSellUseCaseImpl()
    val errorMarketSellLiveData: LiveData<Unit> = marketSellUseCase.errorSellLiveData
    val progressMarketSellLiveData = MutableLiveData<Boolean>()
    val errorNotEnoughProduct: LiveData<String> = marketSellUseCase.errorResponseLiveData
    val connectionErrorMarketSellLiveData = MutableLiveData<Unit>()
    val successMarketSellLiveData = MediatorLiveData<Any>()

    val errorOwnMarketSellLiveData: LiveData<Unit> = marketSellUseCase.errorOwnSellLiveData
    val progressOwnMarketSellLiveData = MutableLiveData<Boolean>()
    val errorOwnNotEnoughProduct: LiveData<String> =
        marketSellUseCase.errorOwnProductResponseLiveData
    val connectionErrorOwnMarketSellLiveData = MutableLiveData<Unit>()
    val successOwnMarketSellLiveData = MediatorLiveData<Any>()

    init {
        //   getCategories()
        getWarehouse()
    }

    fun getWarehouse() {
        if (isConnected()) {
            progressWarehouseLiveData.value = true
            val liveData = warehouseUseCase.getWarehouse()
            successWarehouseLiveData.addSource(liveData) {
                progressWarehouseLiveData.value = false
                successWarehouseLiveData.value = it
                successWarehouseLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorWarehouseLiveData.value = Unit
        }
    }

    fun getOwnCategories() {
        if (isConnected()) {
            progressOwnCategoriesLiveData.value = true
            val liveData = warehouseUseCase.getOwnCategories()
            successOwnCategoriesLiveData.addSource(liveData) {
                progressOwnCategoriesLiveData.value = false
                successOwnCategoriesLiveData.value = it
                successOwnCategoriesLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorOwnCategoriesLiveData.value = Unit
        }
    }


    fun getProducts(categoryId: Int, clientId: String, code: String, name: String) {
        if (isConnected()) {
            progressProductsLiveData.value = true
            val liveData = productsUseCase.getProducts(categoryId.toString(), clientId, code, name)
            successProductsLiveData.addSource(liveData) {
                progressProductsLiveData.value = false
                successProductsLiveData.value = it
                successProductsLiveData.removeSource(liveData)
            }
        } else {
            progressProductsLiveData.value = false
            connectionErrorProductsLiveData.value = Unit
        }
    }

    fun getOwnProducts(categoryId: Int, clientId: String) {
        if (isConnected()) {
            progressOwnProductsLiveData.value = true
            val liveData = productsUseCase.getOwnProducts(categoryId.toString(), clientId)
            successOwnProductsLiveData.addSource(liveData) {
                progressOwnProductsLiveData.value = false
                successOwnProductsLiveData.value = it
                successOwnProductsLiveData.removeSource(liveData)
            }
        } else {
            progressOwnProductsLiveData.value = false
            connectionErrorOwnProductsLiveData.value = Unit
        }
    }


    fun marketSell(list: List<MarketSellData>) {
        if (isConnected()) {
            progressMarketSellLiveData.value = true
            val liveData = marketSellUseCase.marketSell(list)
            successMarketSellLiveData.addSource(liveData) {
                progressMarketSellLiveData.value = false
                successMarketSellLiveData.value = it
                successMarketSellLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorMarketSellLiveData.value = Unit
        }
    }

    fun ownMarketSell(list: List<SellToClientOwnProduct>) {
        if (isConnected()) {
            progressOwnMarketSellLiveData.value = true
            val liveData = marketSellUseCase.marketOwnSell(list)
            successOwnMarketSellLiveData.addSource(liveData) {
                progressOwnMarketSellLiveData.value = false
                successOwnMarketSellLiveData.value = it
                successOwnMarketSellLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorOwnMarketSellLiveData.value = Unit
        }
    }


//
//    fun getCategories() {
//        if (isConnected()) {
//            progressCategoriesLiveData.value = true
//            val liveData = categoriesUseCase.getCategories()
//            successCategoriesLiveData.addSource(liveData) {
//                progressCategoriesLiveData.value = false
//                successCategoriesLiveData.value = it
//                successCategoriesLiveData.removeSource(liveData)
//            }
//        } else {
//            connectionErrorCategoriesLiveData.value = Unit
//        }
//    }

    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch() {
        closeLiveData.value = Unit
    }

}