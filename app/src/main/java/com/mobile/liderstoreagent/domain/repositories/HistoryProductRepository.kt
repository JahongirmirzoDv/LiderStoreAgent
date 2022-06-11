package com.mobile.liderstoreagent.domain.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mobile.liderstoreagent.data.source.remote.retrofit.HistoryClientApi
import com.mobile.liderstoreagent.domain.paging_source.HistoryPagingSource

class HistoryProductRepository(private val apiService: HistoryClientApi) {

    fun getHistory(context: Context, str: String) = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { HistoryPagingSource(context, apiService, str) }
    ).liveData

}