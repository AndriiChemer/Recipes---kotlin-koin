package com.artatech.inkbook.recipes.ui.recipeslist.presentation

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.utils.RecipePreference
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent
import com.artatech.inkbook.recipes.ui.recipeslist.GetRecipesByCategoryUseCase
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
import com.google.gson.Gson

class RecipesViewModel(private val getRecipesUseCase: GetRecipesByCategoryUseCase): ViewModel() {

    private lateinit var category: CategoryIntentModel

    val recipes = MutableLiveData<PaginationRecipesResponse>()
    val toolbarTitle = MutableLiveData<String>()
    val recipesLoadedMore = MutableLiveData<PaginationRecipesResponse>()
    val error = SingleLiveEvent<Throwable>()

    fun getRecipes(category: CategoryIntentModel) {
        val categoryRequest = CategoryRequest(category.categoryId, category.subcategoryId, category.recipeCategoryId)

        getRecipesUseCase(categoryRequest, viewModelScope) { result ->
            result.onSuccess { recipes.value = it }
            result.onFailure { error.value = it }
        }
    }

    @Deprecated("Remove it when migrate from activity to fragment")
    fun loadMoreRecipes(category: CategoryIntentModel, currentPage: Int) {
        val categoryRequest = CategoryRequest(category.categoryId, category.subcategoryId, category.recipeCategoryId, currentPage)

        getRecipesUseCase(categoryRequest, viewModelScope) { result ->
            result.onSuccess { recipesLoadedMore.value = it }
            result.onFailure { error.value = it }
        }
    }

    fun loadMoreRecipes(currentPage: Int) {
        val categoryRequest = CategoryRequest(category.categoryId, category.subcategoryId, category.recipeCategoryId, currentPage)

        getRecipesUseCase(categoryRequest, viewModelScope) { result ->
            result.onSuccess { recipesLoadedMore.value = it }
            result.onFailure { error.value = it }
        }
    }

    fun onViewCreated(key: String, arguments: Bundle?) {
        if (arguments != null) {
            category = arguments.getSerializable(key) as CategoryIntentModel
            toolbarTitle.value = category.title
            getRecipes(category)
        } else {
            error.value = Throwable("Argument with $key key should not be null!")
        }
    }

}