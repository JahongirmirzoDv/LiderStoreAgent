package com.mobile.liderstoreagent.domain.usecases.localusecaseimpl

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors

interface AddClientSelectorsUseCase {
    val errorSelectorsLiveData: LiveData<Unit>
    val errorTimeOutSelect: LiveData<Unit>
    fun getSelectors(): LiveData<AddClientSelectors>
}