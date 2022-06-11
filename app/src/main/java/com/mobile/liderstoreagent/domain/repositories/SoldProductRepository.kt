package com.mobile.liderstoreagent.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mobile.liderstoreagent.data.source.remote.retrofit.SoldHistoryApi
import com.mobile.liderstoreagent.domain.paging_source.SoldProductPagingSource

class SoldProductRepository(private val api: SoldHistoryApi) {

    fun getSoldProducts(str: String) = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SoldProductPagingSource(str, api) }
    ).liveData
}