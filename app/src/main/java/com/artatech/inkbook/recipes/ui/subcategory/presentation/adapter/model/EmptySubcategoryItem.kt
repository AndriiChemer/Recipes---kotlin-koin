package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model

import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.depricated.EMPTY_SUBCATEGORY_VIEW_TYPE

class EmptySubcategoryItem(val id: Int,
                           val name: String,
                           val categoryId: Int): ListRecyclerItem {
    override fun getViewType() = EMPTY_SUBCATEGORY_VIEW_TYPE
}