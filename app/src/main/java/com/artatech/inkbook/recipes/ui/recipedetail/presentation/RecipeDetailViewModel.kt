package com.artatech.inkbook.recipes.ui.recipedetail.presentation

import androidx.lifecycle.ViewModel
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.utils.RecipePreference

class RecipeDetailViewModel: ViewModel() {

    fun addToFavorite(recipe: FullRecipeResponse) {
        RecipePreference.addRecipeToFavorite(recipe)
    }

    fun removeFromFavorite(recipe: FullRecipeResponse) {
        RecipePreference.removeRecipeFromFavorite(recipe)
    }
}