package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.clientmodel.AddClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientdetail.ClientDetail
import com.mobile.liderstoreagent.data.models.clientmodel.Territory
import com.mobile.liderstoreagent.data.models.clientmodel.UpdateClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketCode
import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType
import com.mobile.liderstoreagent.domain.usecases.*
import com.mobile.liderstoreagent.domain.usecases.impl.*
import com.mobile.liderstoreagent.domain.usecases.localusecaseimpl.AddClientSelectorsUseCase
import com.mobile.liderstoreagent.utils.isConnected

class AddClientViewModel : ViewModel() {

    private val selectorsUseCase: AddClientSelectorsUseCase = AddClientSelectorsUseCaseImpl()
    val errorSelectorsLiveData: LiveData<Unit> = selectorsUseCase.errorSelectorsLiveData
    val errorTimeOutLiveDataSelectors: LiveData<Unit> = selectorsUseCase.errorTimeOutSelect
    val progressSelectorsLiveData = MutableLiveData<Boolean>()
    val connectionErrorSelectorsLiveData = MutableLiveData<Unit>()
    val successSelectorsLiveData = MediatorLiveData<AddClientSelectors>()

    private val priceTypeUseCase: PriceTypeUseCase = PriceTypeUseCaseImpl()
    val priceTypeUseCaseSuccess = MediatorLiveData<PriceType>()


    private val territoryUseCase: AgentTerritoriesUseCase = AgentTerritoriesUseCaseImpl()
    val errorTerritoryLiveData: LiveData<Unit> = territoryUseCase.errorTerritoryLiveData
    val progressTerritoryLiveData = MutableLiveData<Boolean>()
    val connectionErrorTerritoryLiveData = MutableLiveData<Unit>()
    val successTerritoryLiveData = MediatorLiveData<List<Territory>>()


    private val clientDetailUseCase: ClientDetailUseCase = ClientDetailUseCaseImpl()
    val progressClientDetailLiveData = MutableLiveData<Boolean>()
    val errorNotFoundLiveData: LiveData<String> = clientDetailUseCase.errorClientDetailNotFoundLiveData
    val connectionErrorClientDetailLiveData = MutableLiveData<Unit>()
    val successClientDetailLiveData = MediatorLiveData<ClientDetail>()



    private val useCase: AddClientUseCase = AddClientUseCaseImpl.getUseCase()
    val errorAddClientLiveData: LiveData<String> = useCase.errorAddClientLiveData
    val errorAddClientServerLiveData: LiveData<String> = useCase.errorAddClientServerLiveData
    val errorTimeOutLiveDataAddClient: LiveData<Unit> = useCase.errorTimeOutAddClientLiveData
    val progressLiveData = MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<MarketCode>()
    val locationLiveData = MediatorLiveData<String>()


    private val updateUseCase: UpdateClientUseCase = UpdateClientUseCaseImpl.getUseCase()
    val errorUpdateClientLiveData: LiveData<String> = updateUseCase.errorUpdateClientLiveData
    val progressUpdateLiveData = MutableLiveData<Boolean>()
    val connectionErrorUpdateLiveData = MutableLiveData<Unit>()
    val successUpdateLiveData = MediatorLiveData<Any>()
    val locationUpdateLiveData = MediatorLiveData<String>()


    init {

        getPriceType()

        val f = useCase.locationLiveData
        locationLiveData.addSource(f) {
            if (it != null) {
                locationLiveData.value = it
                useCase.locationSt = null
            }
        }


    }


    fun getSelectors() {
        if (isConnected()) {
            progressSelectorsLiveData.value = true
            val liveData = selectorsUseCase.getSelectors()
            successSelectorsLiveData.addSource(liveData) {
                progressSelectorsLiveData.value = false
                successSelectorsLiveData.value = it
                successSelectorsLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorSelectorsLiveData.value = Unit
        }

    }

    fun getPriceType() {
        if (isConnected()) {
            val liveData = priceTypeUseCase.getPriceType()
            priceTypeUseCaseSuccess.addSource(liveData) {
                priceTypeUseCaseSuccess.value = it
                priceTypeUseCaseSuccess.removeSource(liveData)
            }
        }
    }


    fun getTerritories() {
        if (isConnected()) {
            progressTerritoryLiveData.value = true
            val liveData = territoryUseCase.getTerritories()
            successTerritoryLiveData.addSource(liveData) {
                progressTerritoryLiveData.value = false
                successTerritoryLiveData.value = it
                successTerritoryLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorTerritoryLiveData.value = Unit
        }

    }

    fun getClientDetail(clientId: Int) {
        if (isConnected()) {
            progressClientDetailLiveData.value = true
            val liveData = clientDetailUseCase.getClientDetail(clientId)
            successClientDetailLiveData.addSource(liveData) {
                progressClientDetailLiveData.value = false
                successClientDetailLiveData.value = it
                successClientDetailLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorClientDetailLiveData.value = Unit
        }

    }


    fun addClient(addClientData: AddClientData) {
        if (isConnected()) {
            progressLiveData.value = true
            val lvd = useCase.addClient(addClientData)
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }

    }


    fun updateClient(addClientData: UpdateClientData) {
        if (isConnected()) {
            progressUpdateLiveData.value = true
            val lvd = updateUseCase.updateClient(addClientData)
            successUpdateLiveData.addSource(lvd) {
                progressUpdateLiveData.value = false
                successUpdateLiveData.value = it
                successUpdateLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorUpdateLiveData.value = Unit
        }

    }

}