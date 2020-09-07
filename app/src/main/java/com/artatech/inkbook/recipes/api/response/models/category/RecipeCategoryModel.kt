package com.artatech.inkbook.recipes.api.response.models.category

import java.io.Serializable

class RecipeCategoryModel(
    val id: Int,
    val name: String,
    val subcategoryId: Int
): Serializable