package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.payment.PaymentData

interface PaymentUseCase {
    val errorPaymentLiveData: LiveData<Unit>
    fun payPayment(paymentData: PaymentData): LiveData<Any>
}