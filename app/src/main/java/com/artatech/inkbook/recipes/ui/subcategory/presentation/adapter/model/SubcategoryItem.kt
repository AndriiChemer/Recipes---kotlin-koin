package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model

import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.depricated.SUBCATEGORY_VIEW_TYPE
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.RecipeCategoryItem

class SubcategoryItem(
    val id: Int,
    val name: String,
    val categoryId: Int,
    var recipeCategories: MutableList<RecipeCategoryItem>,
    var isExpanded: Boolean = false
): ListRecyclerItem {
    override fun getViewType() =
        SUBCATEGORY_VIEW_TYPE
}