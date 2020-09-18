package com.artatech.inkbook.recipes.ui.recipesfavoritelist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.utils.RecipePreference

class RecipesFavoriteViewModel: ViewModel() {

    val favoriteRecipes = MutableLiveData<List<FullRecipeResponse>>()

    fun onViewCreated() {
        getRecipeFromPreferences()
    }

    private fun getRecipeFromPreferences() {
        val favoriteRecipe =  RecipePreference.recipeFavoriteMap?.values?.toList() ?: emptyList()
        favoriteRecipes.value = favoriteRecipe
    }

    fun removeFromFavorite(item: FullRecipeResponse) {
        RecipePreference.removeRecipeFromFavorite(item)
    }
}