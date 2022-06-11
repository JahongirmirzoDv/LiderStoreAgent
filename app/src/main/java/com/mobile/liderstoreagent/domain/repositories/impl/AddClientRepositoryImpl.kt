package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.app.App
import com.mobile.liderstoreagent.data.models.clientmodel.AddClientData
import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.MarketCode
import com.mobile.liderstoreagent.data.source.remote.retrofit.AddClientApi
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.domain.repositories.repo.AddClientRepository
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class AddClientRepositoryImpl : AddClientRepository {
    private val api = ApiClient.retrofit.create(AddClientApi::class.java)
    override val errorTimeOutData = MutableLiveData<Unit>()
    override suspend fun addClient(data: AddClientData): Flow<Result<Pair<Int, MarketCode?>>> =
        flow {

            try {
                val image = Compressor.compress(App.instance, data.image)
                // pass it like this
                val requestFile: RequestBody =
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("image", image.name, requestFile)

                val marketName = data.marketName.toRequestBody("text/plain".toMediaTypeOrNull())
                val address = data.address.toRequestBody("text/plain".toMediaTypeOrNull())
                val responsiblePerson =
                    data.responsiblePerson.toRequestBody("text/plain".toMediaTypeOrNull())
                val reqPhone1 = data.directorPhone.toRequestBody("text/plain".toMediaTypeOrNull())
                val reqPhone2 = data.workPhone.toRequestBody("text/plain".toMediaTypeOrNull())
                val INN = data.INN.toRequestBody("text/plain".toMediaTypeOrNull())
                val director = data.directorName.toRequestBody("text/plain".toMediaTypeOrNull())
                val birthdate = data.birthDate.toRequestBody("text/plain".toMediaTypeOrNull())
                val target = data.target.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = api.addClient(
                    "application/json",
                    marketName,
                    address,
                    data.latitude,
                    data.longitude,
                    reqPhone1,
                    reqPhone2,
                    INN,
                    director,
                    birthdate,
                    responsiblePerson,
                    body,
                    data.agentId,
                    data.car,
                    target,
                    data.market,
                    data.territory,
                    data.price_type
                )
                when (response.code()) {
                    201 -> {
                        emit(Result.success(Pair(201, response.body())))
                    }
                    else -> {
                        emit(Result.success(Pair(response.code(), null)))
                    }
                }
            } catch (e: Exception) {
                errorTimeOutData.postValue(Unit)
            }
        }
}