package com.artatech.inkbook.recipes.api.response.models.recipe

class RecipeResponse(
    val id: Int,
    val name: String,
    val comment: String,
    val imageUrl: String,
    val createAt: Long,
    val portionCount: String?,
    val cookTime: String?,
    val isActive: Int?
)