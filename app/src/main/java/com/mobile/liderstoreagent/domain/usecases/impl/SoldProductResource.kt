package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.paging.PagingData
import com.mobile.liderstoreagent.data.models.historymodel.other.Result


sealed class SoldProductResource {

    object Loading: SoldProductResource()

    class Success(val list: PagingData<Result>): SoldProductResource()

    class Error(val message: String): SoldProductResource()
}