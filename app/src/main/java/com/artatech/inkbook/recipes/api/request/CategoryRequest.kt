package com.artatech.inkbook.recipes.api.request

import com.artatech.inkbook.recipes.api.RECIPE_PER_PAGE
import java.io.Serializable

class CategoryRequest(
    val categoryId: Int,
    val subcategoryId: Int,
    val recipeCategoryId: Int?,
    var currentPage: Int? = null,
    var numberPerPage: Int? = RECIPE_PER_PAGE
): Serializable