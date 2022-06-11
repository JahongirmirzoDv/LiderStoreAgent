package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel : ViewModel() {

    val selectPageLiveData = MutableLiveData<Int>()
    private var currentPage = 0

    fun selectPagePosition(pos: Int) {
        if (currentPage != pos) {
            selectPageLiveData.value = pos
            currentPage = pos
        }
    }
}