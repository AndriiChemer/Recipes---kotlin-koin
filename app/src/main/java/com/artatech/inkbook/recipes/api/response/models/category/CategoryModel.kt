package com.artatech.inkbook.recipes.api.response.models.category

import java.io.Serializable

data class CategoryModel(
    val id: Int,
    val name: String,
    val imageName: String?,
    val subcategories: List<SubcategoryModel>
): Serializable