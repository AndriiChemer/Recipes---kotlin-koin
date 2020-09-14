package com.artatech.inkbook.recipes.api.response.models.recipe

import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.ui.recipedetail.presentation.COOK_STEP_VIEW_TYPE

class CookStepResponse(
    val id: Int,
    val step: Int,
    val description: String,
    val imageUrl: String
): ListRecyclerItem {
    override fun getViewType() = COOK_STEP_VIEW_TYPE
}