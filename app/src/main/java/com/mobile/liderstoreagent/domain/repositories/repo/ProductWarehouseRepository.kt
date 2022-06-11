package com.mobile.liderstoreagent.domain.repositories.repo

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ProductWarehouseApi
import com.mobile.liderstoreagent.domain.paging_source.OwnProductPagingSource

class ProductWarehouseRepository(private val apiService: ProductWarehouseApi) {
     fun getProducts(context: Context, str: String) = Pager(
    PagingConfig(
    pageSize = 10,
    enablePlaceholders = false
    ),
    pagingSourceFactory = { OwnProductPagingSource(context, apiService, str) }
    ).liveData
}