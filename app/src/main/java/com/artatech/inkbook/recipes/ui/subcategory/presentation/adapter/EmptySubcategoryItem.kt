package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter

import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem

class EmptySubcategoryItem(val id: Int,
                           val name: String,
                           val categoryId: Int): ListRecyclerItem {
    override fun getViewType() = EMPTY_SUBCATEGORY_VIEW_TYPE
}