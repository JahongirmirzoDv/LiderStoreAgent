package com.mobile.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData
import com.mobile.liderstoreagent.domain.repositories.repo.CategoryRepository
import com.mobile.liderstoreagent.domain.repositories.impl.CategoryRepositoryImpl
import com.mobile.liderstoreagent.domain.usecases.CategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class CategoriesUseCaseImpl : CategoriesUseCase {

    private val repository: CategoryRepository = CategoryRepositoryImpl()
    override val errorCategoriesLiveData = MutableLiveData<Unit>()

    override fun getCategories(): LiveData<List<CategoryData>> = liveData(Dispatchers.IO) {
        repository.getCategories().collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorCategoriesLiveData.postValue(Unit)
            }
        }
    }

}