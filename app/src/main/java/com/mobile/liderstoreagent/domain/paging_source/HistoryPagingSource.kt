package com.mobile.liderstoreagent.domain.paging_source

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobile.liderstoreagent.data.models.historymodel.other.Result
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.HistoryClientApi
import com.mobile.liderstoreagent.utils.Constants

class HistoryPagingSource(
    private val context: Context,
    private val apiService: HistoryClientApi,
    private var str: String
) : PagingSource<Int, Result>() {

    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, Result> {

        val position = params.key ?: Constants.PAGE_STARTING_INDEX

        return try {

            val response = apiService.getHistory(
                "${Constants.BASE_URL}order/to-agent-sell-order/",
                "token ${TokenSaver.token}",
                10,
            position,
                str
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