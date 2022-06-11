package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.PaymentApi
import com.mobile.liderstoreagent.domain.repositories.repo.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class PaymentRepoImpl : PaymentRepository {

    private val api = ApiClient.retrofit.create(PaymentApi::class.java)
    override suspend fun payPayment(payment1: PaymentData): Flow<Result<Any?>> = flow {
        val paymentType = payment1.payment_type.toRequestBody("text/plain".toMediaTypeOrNull())

        try {
            val response = api.payPayment(
                "application/json",
                payment1.client,
                paymentType,
                payment1.payment,
                payment1.productId,
                payment1.comment
            )
            if (response.code() == 201) {
                emit(Result.success(response.body()))
            } else if (response.code() == 400) {
                emit(kotlin.Result.success(response.body()))
            }
        } catch (e: Exception) {
        }
    }
}