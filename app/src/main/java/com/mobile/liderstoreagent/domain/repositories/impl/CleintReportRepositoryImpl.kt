package com.mobile.liderstoreagent.domain.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.mobile.liderstoreagent.app.App
import com.mobile.liderstoreagent.data.models.reportmodel.ClientReportData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ClientReportApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.ClientReportRepository
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class ClientReportRepositoryImpl : ClientReportRepository {

    override val errorTimeOutData = MutableLiveData<Unit>()


    private val api = ApiClient.retrofit.create(ClientReportApiInterface::class.java)



    override suspend fun clientReportSend(data: ClientReportData): Flow<Result<Any?>> = flow {

        try {
            val parts = ArrayList<MultipartBody.Part>()

            for (element in data.images) {
                val image = Compressor.compress(App.instance, element)
                // pass it like this
                val requestFile: RequestBody =
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("images", image.name, requestFile)
                parts.add(body)
            }

            val comment = data.comment.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.sendClientReport(
                "application/json",
                comment,
                parts,
                data.client,
                data.agent
            )

            when (response.code()) {
                201 -> {
                    emit(Result.success(true))
                }
                else -> {
                    emit(Result.success(false))
                }
            }
        } catch (e: Exception) {
            errorTimeOutData.postValue(Unit)
        }
    }


}