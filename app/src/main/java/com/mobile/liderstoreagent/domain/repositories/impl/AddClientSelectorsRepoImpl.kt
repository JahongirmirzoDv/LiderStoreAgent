package com.mobile.liderstoreagent.domain.repositories.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.AddClientSelectors
import com.mobile.liderstoreagent.data.source.remote.retrofit.AddClientSelectorsApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.domain.repositories.repo.AddClientSelectorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddClientSelectorsRepoImpl : AddClientSelectorsRepository {
    override val errorTimeOutData = MutableLiveData<Unit>()
    private val api = ApiClient.retrofit.create(AddClientSelectorsApi::class.java)
    override suspend fun getAddClientSelectors(): Flow<Result<AddClientSelectors?>> = flow {
        try {
            val response = api.getClientSelectors("client/client-options-list/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }

        } catch (e: Exception) {
            Log.d("ddddd",e.message.toString())
            errorTimeOutData.postValue(Unit)
        }
    }
}