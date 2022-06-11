package com.mobile.liderstoreagent.domain.repositories

import com.mobile.liderstoreagent.app.App
import com.mobile.liderstoreagent.data.models.clientmodel.UpdateClientData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.UpdateClientApi
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class UpdateClientRepositoryImpl : UpdateClientRepository {
    private val api = ApiClient.retrofit.create(UpdateClientApi::class.java)

    override suspend fun updateClient(data: UpdateClientData): Flow<Result<Any?>> = flow {

        try {

            val response = if (data.image == null) {

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

                api.updateClient(
                    "client/client-detail/${data.id}/",
                    data.id,
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
                    null,
                    data.agentId,
                    data.car,
                    target,
                    data.market,
                    data.territory,
                    data.price_type
                )

            } else {

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

                api.updateClient(
                    "client/client-detail/${data.id}/",
                    data.id,
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
            }


            when (response.code()) {
                200 -> {
                    emit(Result.success(true))
                }
                else -> {
                    emit(Result.success(false))
                }
            }

        } catch (e: Exception) {

        }
    }
}