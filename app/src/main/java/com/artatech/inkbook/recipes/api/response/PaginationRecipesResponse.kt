package com.artatech.inkbook.recipes.api.response

import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse

class PaginationRecipesResponse(
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int,
    val recipes: List<FullRecipeResponse>
)