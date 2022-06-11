package com.mobile.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData

interface CategoriesUseCase {
    val errorCategoriesLiveData : LiveData<Unit>
    fun getCategories() : LiveData<List<CategoryData>>

}