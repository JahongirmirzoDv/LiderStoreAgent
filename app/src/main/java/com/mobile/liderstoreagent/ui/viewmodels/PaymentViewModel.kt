package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.liderstoreagent.data.models.payment.PaymentData
import com.mobile.liderstoreagent.domain.usecases.ClientProductsUseCase
import com.mobile.liderstoreagent.domain.usecases.PaymentUseCase
import com.mobile.liderstoreagent.domain.usecases.impl.ClientProductUseCaseImpl
import com.mobile.liderstoreagent.domain.usecases.impl.PaymentUseCaseImpl
import com.mobile.liderstoreagent.utils.isConnected

class PaymentViewModel : ViewModel() {


    private val useCase: PaymentUseCase = PaymentUseCaseImpl()
    private val productsUseCase: ClientProductsUseCase = ClientProductUseCaseImpl()
    val errorPaymentLiveData: LiveData<Unit> = useCase.errorPaymentLiveData
    val progressPaymentData = MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successPaymentLiveData = MediatorLiveData<Any>()


    fun payPayment(payment: PaymentData) {
        if (isConnected()) {
            progressPaymentData.value = true
            val liveData = useCase.payPayment(payment)
            successPaymentLiveData.addSource(liveData) {
                progressPaymentData.value = false
                successPaymentLiveData.value = it
                successPaymentLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }

    }

    fun getClientProducts(clientId: Int) {
        if (isConnected()) {
            progressPaymentData.value = true
            val liveData = productsUseCase.getClientProducts(clientId.toString())
            successPaymentLiveData.addSource(liveData) {
                progressPaymentData.value = false
                successPaymentLiveData.value = it
                successPaymentLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }
    }



}