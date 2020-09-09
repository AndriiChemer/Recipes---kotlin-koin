package com.artatech.inkbook.recipes.api.response.models

import com.artatech.inkbook.recipes.api.response.models.recipe.*
import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RECIPE_VIEW_TYPE

class FullRecipeResponse(
    val recipe: RecipeResponse,
    val ingredients: List<IngredientResponse>,
    val energies: List<EnergyResponse>,
    val cookSteps: List<CookStepResponse>,
    val tags: List<TagResponse>,
    val kitchens: List<KitchenResponse>
): ListRecyclerItem {
    override fun getViewType() = RECIPE_VIEW_TYPE
}