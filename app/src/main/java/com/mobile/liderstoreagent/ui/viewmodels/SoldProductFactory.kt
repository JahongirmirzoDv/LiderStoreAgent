package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.liderstoreagent.utils.NetworkHelper

class SoldProductFactory(private val networkHelper: NetworkHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SoldProductViewModel::class.java)) {
            return SoldProductViewModel(networkHelper) as T
        }
        throw Exception("Error")
    }
}