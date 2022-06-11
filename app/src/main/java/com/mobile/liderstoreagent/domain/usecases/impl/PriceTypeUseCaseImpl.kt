package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.clientmodel.pricetype.PriceType
import com.mobile.liderstoreagent.domain.repositories.impl.PriceTypeRepoImpl
import com.mobile.liderstoreagent.domain.repositories.repo.PriceTypeRepo
import com.mobile.liderstoreagent.domain.usecases.PriceTypeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class PriceTypeUseCaseImpl : PriceTypeUseCase {

    private val repository: PriceTypeRepo = PriceTypeRepoImpl()


    override fun getPriceType(): LiveData<PriceType> = liveData(Dispatchers.IO) {
        repository.getPriceType().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            }
        }
    }
}
