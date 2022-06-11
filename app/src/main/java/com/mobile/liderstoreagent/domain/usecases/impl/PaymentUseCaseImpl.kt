package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.domain.repositories.impl.PaymentRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.PaymentRepository
import com.mobile.liderstoreagent.domain.usecases.PaymentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class PaymentUseCaseImpl : PaymentUseCase {

    private val repository: PaymentRepository = PaymentRepoImpl()
    override val errorPaymentLiveData = MutableLiveData<Unit>()

    override fun payPayment(paymentData: PaymentData): LiveData<Any> = liveData(Dispatchers.IO) {
        repository.payPayment(paymentData).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorPaymentLiveData.postValue(Unit)
            }
        }
    }
}
