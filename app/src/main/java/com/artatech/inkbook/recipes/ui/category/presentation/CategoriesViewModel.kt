package com.artatech.inkbook.recipes.ui.category.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.ui.category.GetCategoriesUseCase
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent

class CategoriesViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {
    val categories = MutableLiveData<List<CategoryModel>>()
    val error = SingleLiveEvent<Throwable>()
    val navigateToSubcategory = SingleLiveEvent<CategoryModel>()

    fun onViewCreated() {
        getCategories()
    }

    private fun getCategories() {
        getCategoriesUseCase(viewModelScope) { result ->
            result.onSuccess { categories.value = it }
            result.onFailure { error.value = it }
        }
    }

    fun categoryClicked(category: CategoryModel) {
        navigateToSubcategory.value = category
    }
}