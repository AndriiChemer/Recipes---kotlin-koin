package com.artatech.inkbook.recipes.ui.recipeshortdetail.presentation

import androidx.lifecycle.ViewModel
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.utils.RecipePreference

class RecipeShortDetailViewModel: ViewModel() {
    fun addToFavorite(recipe: FullRecipeResponse) {
        RecipePreference.addRecipeToFavorite(recipe)
    }

    fun removeFromFavorite(recipe: FullRecipeResponse) {
        RecipePreference.removeRecipeFromFavorite(recipe)
    }
}