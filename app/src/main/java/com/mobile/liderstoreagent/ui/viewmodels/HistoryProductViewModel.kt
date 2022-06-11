package com.mobile.liderstoreagent.ui.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.mobile.liderstoreagent.data.models.historymodel.other.Result
import com.mobile.liderstoreagent.domain.paging_source.DataWrapper
import com.mobile.liderstoreagent.domain.repositories.HistoryProductRepository

/**
 *Created by farrukh_kh on 6/22/21 4:08 PM
 *uz.algorithmgateway.uymarket.vm
 **/

class HistoryProductViewModel(
    private val context: Context,
    private val repository: HistoryProductRepository
) : ViewModel() {

    private val _historyState = MutableLiveData<DataWrapper<List<Result>>>()
    val historyState: LiveData<DataWrapper<List<Result>>> get() = _historyState

    fun getHistory(str: String) = repository.getHistory(context, str).cachedIn(viewModelScope)
}

class HistoryViewModelFactory(
    private val context: Context,
    private val repository: HistoryProductRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryProductViewModel::class.java)) {
            return HistoryProductViewModel(context, repository) as T
        } else if (modelClass.isAssignableFrom(OwnProductViewModel::class.java)) {
            return OwnProductViewModel(context) as T
        }
        throw IllegalArgumentException("$modelClass is not FurnitureViewModel")
    }
}
