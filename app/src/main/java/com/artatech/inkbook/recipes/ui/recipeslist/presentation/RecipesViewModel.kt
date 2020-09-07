package com.artatech.inkbook.recipes.ui.recipeslist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent
import com.artatech.inkbook.recipes.ui.recipeslist.GetRecipesByCategoryUseCase
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel

class RecipesViewModel(private val getRecipesUseCase: GetRecipesByCategoryUseCase): ViewModel() {

    val recipes = MutableLiveData<PaginationRecipesResponse>()
    val recipesLoadedMore = MutableLiveData<PaginationRecipesResponse>()
    val error = SingleLiveEvent<Throwable>()

    fun getRecipes(category: CategoryIntentModel) {
        val categoryRequest = CategoryRequest(category.categoryId, category.subcategoryId, category.recipeCategoryId)

        getRecipesUseCase(categoryRequest, viewModelScope) { result ->
            result.onSuccess { recipes.value = it }
            result.onFailure { error.value = it }
        }
    }

    fun loadMoreRecipes(category: CategoryIntentModel, currentPage: Int) {
        val categoryRequest = CategoryRequest(category.categoryId, category.subcategoryId, category.recipeCategoryId, currentPage)

        getRecipesUseCase(categoryRequest, viewModelScope) { result ->
            result.onSuccess { recipesLoadedMore.value = it }
            result.onFailure { error.value = it }
        }
    }
}