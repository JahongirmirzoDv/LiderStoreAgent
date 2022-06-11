package com.mobile.liderstoreagent.domain.repositories.impl

import android.util.Log
import com.mobile.liderstoreagent.app.App
import com.mobile.liderstoreagent.data.models.reportmodel.ReportData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.ReportApiInterface
import com.mobile.liderstoreagent.domain.repositories.repo.ReportRepository
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class ReportRepositoryImpl : ReportRepository {
    private val api = ApiClient.retrofit.create(ReportApiInterface::class.java)

    override suspend fun reportSend(data: ReportData): Flow<Result<Any?>> = flow {

        try {
            val response = if (data.image == null) {

                val comment = data.comment.toRequestBody("text/plain".toMediaTypeOrNull())
                api.sendReport(
                    "application/json", comment,
                    null, data.sale_agent
                )

            } else {

                val image = Compressor.compress(App.instance, data.image)
                // pass it like this
                val requestFile: RequestBody =
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("image", image.name, requestFile)

                val comment = data.comment.toRequestBody("text/plain".toMediaTypeOrNull())

                api.sendReport(
                    "application/json", comment,
                    body, data.sale_agent
                )
            }

            when (response.code()) {
                201 -> {
                    emit(Result.success(true))
                }
                else -> {
                    emit(Result.success(false))
                }
            }
        } catch (e: Exception) {
            Log.d("SELL", "exception = $e")
        }
    }
}