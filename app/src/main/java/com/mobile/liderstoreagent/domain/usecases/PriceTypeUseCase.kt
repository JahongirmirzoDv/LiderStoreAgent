package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType

interface PriceTypeUseCase {

    fun getPriceType(): LiveData<PriceType>
}