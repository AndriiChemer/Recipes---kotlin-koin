package com.artatech.inkbook.recipes.ui.recipeslist

import com.artatech.inkbook.recipes.api.Repository
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.RecipeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetRecipesByCategoryUseCase(private val repository: Repository) {

    operator fun invoke(categoryRequest: CategoryRequest, coroutineScope: CoroutineScope, onResult: (Result<PaginationRecipesResponse>) -> Unit) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { repository.getRecipesByCategoryId(categoryRequest) }
            }

            onResult(result)
        }

    }
}