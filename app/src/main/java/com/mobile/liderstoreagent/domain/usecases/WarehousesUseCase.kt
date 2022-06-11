package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.warehouse.AgentData
import com.mobile.liderstoreagent.data.models.warehouse.WarehouseData

interface WarehousesUseCase {

    val errorWarehousesLiveData : LiveData<Unit>
    val errorOwnCategoriesLiveData : LiveData<Unit>
    fun getWarehouse(): LiveData<WarehouseData>
    fun getOwnCategories(): LiveData<AgentData>

}