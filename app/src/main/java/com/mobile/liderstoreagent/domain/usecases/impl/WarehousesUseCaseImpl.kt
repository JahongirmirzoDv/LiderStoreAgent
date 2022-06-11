package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData
import com.mobile.liderstoreagent.domain.repositories.impl.WarehouseRepositoryImpl
import com.mobile.liderstoreagent.domain.repositories.repo.WarehouseRepository
import com.mobile.liderstoreagent.domain.usecases.WarehousesUseCase
import kotlinx.coroutines.flow.collect

class WarehousesUseCaseImpl : WarehousesUseCase {

    private val repository: WarehouseRepository = WarehouseRepositoryImpl()

    override val errorWarehousesLiveData =
        MutableLiveData<Unit>()

    override val errorOwnCategoriesLiveData =
        MutableLiveData<Unit>()



    override fun getWarehouse(): LiveData<WarehouseData> = liveData {
        repository.getWarehouses().collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorWarehousesLiveData.postValue(Unit)
            }
        }
    }

    override fun getOwnCategories(): LiveData<AgentData> = liveData {
        repository.getOwnCategories().collect{
            if (it.isSuccess){
                it.getOrNull()?.let { it1 ->emit(it1) }
            }else{
                errorOwnCategoriesLiveData.postValue(Unit)
            }
        }
    }
}