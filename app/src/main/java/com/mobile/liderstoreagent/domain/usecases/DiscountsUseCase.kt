package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.discountsmodel.Discounts

interface DiscountsUseCase {
    val errorDiscountsLiveData : LiveData<Unit>
    fun getDiscounts() : LiveData<List<Discounts>>
}