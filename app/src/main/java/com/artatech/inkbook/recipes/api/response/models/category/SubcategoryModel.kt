package com.artatech.inkbook.recipes.api.response.models.category

import com.artatech.inkbook.recipes.api.response.models.category.RecipeCategoryModel
import java.io.Serializable

class SubcategoryModel(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val recipeCategories: List<RecipeCategoryModel>
): Serializable