package com.mobile.liderstoreagent.domain.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobile.liderstoreagent.data.models.soldownproductlist.Result
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.SoldHistoryApi
import com.mobile.liderstoreagent.utils.Constants

class SoldProductPagingSource(
    private var str: String,
    private var apiService: SoldHistoryApi
) : PagingSource<Int, com.mobile.liderstoreagent.data.models.soldownproductlist.Result>() {

    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, com.mobile.liderstoreagent.data.models.soldownproductlist.Result> {

        val position = params.key ?: Constants.PAGE_STARTING_INDEX

        return try {

            val response = apiService.soldProducts(
                "token ${TokenSaver.token}",
                10,
                position,
                str, "", ""
            )

            val pagedResponse = response.body()

            LoadResult.Page(
                data = pagedResponse?.results.orEmpty(),
                prevKey = null,
                nextKey = if (pagedResponse?.next == null) null else position + 10
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}