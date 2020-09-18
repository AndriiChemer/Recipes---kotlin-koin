package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model

import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.depricated.RECIPE_CATEGORY_VIEW_TYPE

class RecipeCategoryItem(val id: Int,
                         val name: String,
                         val subcategoryId: Int): ListRecyclerItem {
    override fun getViewType() = RECIPE_CATEGORY_VIEW_TYPE
}