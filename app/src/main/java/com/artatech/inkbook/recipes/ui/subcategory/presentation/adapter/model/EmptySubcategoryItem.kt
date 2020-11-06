package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model

import com.artatech.inkbook.recipes.app.di.RECIPE_IMAGE_WITHOUT_SUFFIX
import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.depricated.EMPTY_SUBCATEGORY_VIEW_TYPE

class EmptySubcategoryItem(val id: Int,
                           val name: String,
                           val categoryId: Int,
                           val imageName: String?): ListRecyclerItem {
    override fun getViewType() = EMPTY_SUBCATEGORY_VIEW_TYPE

    fun getImageUrl(): String? {
        return if (imageName != null) {
            RECIPE_IMAGE_WITHOUT_SUFFIX + imageName
        } else {
            null
        }
    }
}