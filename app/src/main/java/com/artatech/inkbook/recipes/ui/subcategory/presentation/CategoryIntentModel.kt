package com.artatech.inkbook.recipes.ui.subcategory.presentation

import java.io.Serializable

class CategoryIntentModel(val categoryId: Int,
                          val subcategoryId: Int,
                          val recipeCategoryId: Int?,
                          val title: String): Serializable {
}